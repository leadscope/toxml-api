/**
 * Stucco ToxML API - a Java interface for parsing and creating ToxML documents
 * Copyright (C) 2011 Scott Miller - Leadscope, Inc.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.leadscope.stucco.builder;

import java.io.*;
import java.util.*;

import nu.xom.*;

/**
 * Generates the source code for the toxml model based on the specification export
 */
public class ToxmlModelGenerator {
  private Element rootElement;
  
  private Map<String, Metric> metricsMap = new HashMap<String, Metric>();
  private Map<String, ToxmlClass> typesMap = createPrimitiveTypeMap();
  private Map<String, Element> elementIdMap = new HashMap<String, Element>();
  private Map<String, CompositeMember> elementIdCompositeMemberMap = new HashMap<String, CompositeMember>();
  private Map<String, List<ToxmlClassParent>> typeNamesToParents = new HashMap<String, List<ToxmlClassParent>>();

  private List<ToxmlClass> toxmlClasses = new ArrayList<ToxmlClass>();
  private ToxmlClass compoundClass;
  private GeneratedModelClass generatedModelClass = new GeneratedModelClass();
  private List<String> typesToSkip = Arrays.asList("Boolean");
  private Set<String> allStudyTypeNames = new HashSet<String>();
  
  public ToxmlModelGenerator(InputStream is) throws Exception {
    Builder parser = new Builder();
    Document doc = parser.build(is);
    rootElement = doc.getRootElement();
        
    addMetrics();
    addTypes();
    createClasses();
    renameStudyTreatmentTypes();
    updateVocabularyDependentFields();
    mergeEquivalentTypes();
    
    List<String> typeNames = new ArrayList<String>(typesMap.keySet());
    Collections.sort(typeNames);
    for (String typeName : typeNames) {      
      toxmlClasses.add(typesMap.get(typeName));
    }
        
    compoundClass = typesMap.get("CompoundRecord"); 
    generatedModelClass.setCompoundClass(compoundClass);
  }
  
  public ToxmlModelGenerator(File specificationFile) throws Exception {
    this(new FileInputStream(specificationFile));
  }
  
  public List<ToxmlClass> getToxmlClasses() {
    return toxmlClasses;
  }
  
  public ToxmlClass getCompoundClass() {
    return compoundClass;
  }

  public ToxmlClass getGeneratedModelClass() {
    return generatedModelClass;
  }
  
  private void addMetrics() {
    Elements nodes = rootElement.getChildElements(Spec.METRIC);
    for (int i = 0; i < nodes.size(); i++) {
      addMetric(nodes.get(i));
    }
  }
  
  private void addMetric(Element node) {
    Metric metric = new Metric();
    metric.setName(stringValue(node, "name"));
    metric.setCaseSensitive(booleanValue(node, "caseSensitive"));
    Elements termElements = node.getChildElements("term");
    for (int i = 0; i < termElements.size(); i++) {
      Element termElement = termElements.get(i);
      Units units = new Units();
      units.setName(termElement.getFirstChildElement("name").getValue());
      units.setMagnitude(longValue(termElement, "magnitude"));
      units.setFractionOfBase(booleanValue(termElement, "fractionOfBase"));
      metric.addUnits(units);
    }
    metricsMap.put(metric.getName(), metric);
    generatedModelClass.addMetric(metric);
  }

  private void addTypes() {
    Elements nodes = rootElement.getChildElements(Spec.TYPE);
    for (int i = 0; i < nodes.size(); i++) {
      addType(nodes.get(i));
    }
  }
  
  private void addType(Element node) {
    String typeName = stringValue(node, Spec.NAME);
    if (typesToSkip.contains(typeName)) {
      return;
    }
    
    Element valuesElement = node.getFirstChildElement(Spec.ENUMERATED_VALUES);
    if (valuesElement != null) {
      Elements valueElements = valuesElement.getChildElements(Spec.VALUE);
      if (valueElements.size() > 0) {
        EnumeratedTypeClass type = new EnumeratedTypeClass();
        type.setName(typeName);
        for (int i = 0; i < valueElements.size(); i++) {
          Element valueElement = valueElements.get(i);
          type.addValue(valueElement.getValue());
        }
        type.setDescription(optionalStringValue(node, Spec.DESCRIPTION));
        addToTypesMap(type);
        System.out.println("Added type: " + type);
      }
    }    
    else {
      Element complexDefElement = node.getFirstChildElement(Spec.ELEMENT);
      if (complexDefElement != null) {
        if (typeName.endsWith(Spec.STUDY_TREATMENT)) {
          String studyTypeName = typeName.substring(0, typeName.length() - Spec.STUDY_TREATMENT.length());
          ToxmlClass createdClass = createClass(complexDefElement);
          typesMap.remove(createdClass.getName());          
          createdClass.setName(typeName);
          createdClass.setStudyTypeName(studyTypeName);
          createdClass.setDescription(optionalStringValue(node, Spec.DESCRIPTION));
          addToTypesMap(createdClass);
          System.out.println("Added treatment type: " + createdClass);
        }
        else {
          throw new RuntimeException("Not currently supporting complex types, other than treatments");
        }
      }
    }
  }

  private void addToTypesMap(ToxmlClass type) {
    if (type.getName() == null) {
      throw new IllegalArgumentException("Tried to add type with null name: " + type.getClass());
    }
    typesMap.put(type.getName(), type);
  }
  
  private void createClasses() {
    Elements nodes = rootElement.getChildElements(Spec.ELEMENT);
    for (int i = 0; i < nodes.size(); i++) {
      createClass(nodes.get(i));
    }
  }
  
  private ToxmlClass createClass(Element node) {
    String typeName = stringValue(node, Spec.TYPE);

    String elementId = stringValue(node, Spec.ELEMENT_ID);
    elementIdMap.put(elementId, node);
    
    if (Spec.COMPOSITE.equals(typeName)) {
      return createComposite(node);
    }
    else if (Spec.SET.equals(typeName)) {
      return createCollection(node, true);
    }
    else if (Spec.LIST.equals(typeName)) {
      return createCollection(node, false);
    }
    
    throw new RuntimeException("Cannot create class of type: " + typeName);
  }  

  private ToxmlClass createCollection(Element node, boolean isSet) {
    CollectionClass collection;
    if (isStudyList(node)) {
      collection = new StudyListClass();
      collection.setStudyTypeName(getStudyTypeName(node));
    }
    else if (isSet) {
      if (hasCompositeChild(node)) {
        collection = new ListClass();
      }
      else {
        collection = new SetClass();
      }
    }
    else {
      collection = new ListClass();
    }
    
    Elements childElements = node.getChildElements(Spec.ELEMENT);
    if (childElements.size() != 1) {
      throw new RuntimeException("List should have exactly 1 child element, instead has: " + childElements.size());
    }
    
    Element childElement = childElements.get(0);    
    String childTag = stringValue(childElement, Spec.TAG);      
    ToxmlClass childClass = createOrAddType(childElement);
    
    collection.setChildTag(childTag);
    collection.setChildClass(childClass);
    addChildReference(childClass, collection);
    
    return nameCheckAndAddType(node, collection);
  }

  private ToxmlClass createComposite(Element node) {
    CompositeClass composite;
    if (isToxicityStudies(node)) {
      composite = new ToxicityStudiesClass();
    }
    else if (isStudy(node)) {
      composite = new StudyClass();
    }
    else if (isTest(node)) {
      composite = new TestClass();
    }
    else if (isTreatment(node)) {
      throw new RuntimeException("All treatments should be handled by a shared complex type");      
    }
    else {
      composite = new CompositeClass();
    }
        
    Elements childElements = node.getChildElements(Spec.ELEMENT);
    for (int i = 0; i < childElements.size(); i++) {
      Element childElement = childElements.get(i);
      String childElementId = stringValue(childElement, Spec.ELEMENT_ID); 
      elementIdMap.put(childElementId, childElement);
      
      String childTag = stringValue(childElement, Spec.TAG);      
      ToxmlClass childClass = createOrAddType(childElement);
      
      CompositeMember member = composite.addMember(childTag, childClass);
      addChildReference(childClass, member);
      composite.setDescription(childTag, optionalStringValue(childElement, Spec.DESCRIPTION));
      member.setElementId(childElementId);
      elementIdCompositeMemberMap.put(childElementId, member);
                  
      if (childClass.isHasUnits()) {
        Element prefUnitsElement = childElement.getFirstChildElement(Spec.PREFERRED_UNITS);
        if (prefUnitsElement != null) {
          Elements valueElements = prefUnitsElement.getChildElements(Spec.VALUE);
          for (int j = 0; j < valueElements.size(); j++) {
            String prefUnits = valueElements.get(j).getValue();
            composite.addPreferredUnit(childTag, prefUnits);
          }
        }
      }
      
      if (isStringValue(childElement, Spec.TYPE, Spec.STRING)) {
        addVocabularyToMember(childElement, member);
      }
    }
    
    return nameCheckAndAddType(node, composite);
  }  
  
  private void addChildReference(ToxmlClass childClass, ToxmlClassParent parent) {
    List<ToxmlClassParent> parents = typeNamesToParents.get(childClass.getName());
    if (parents == null) {
      parents = new ArrayList<ToxmlClassParent>();
      typeNamesToParents.put(childClass.getName(), parents);
    }
    parents.add(parent);
  }
  
  private void updateChildReferences(String oldStudyTypeName, ToxmlClass newClass) {
    List<ToxmlClassParent> parents = typeNamesToParents.get(oldStudyTypeName);
    if (parents != null) {
      for (ToxmlClassParent parent : parents) {
        parent.setChildClass(newClass);
      }
    }
    typeNamesToParents.remove(oldStudyTypeName);
    
    List<ToxmlClassParent> newParents = typeNamesToParents.get(newClass.getName());
    if (newParents == null) {
      typeNamesToParents.put(newClass.getName(), parents);
    }
    else {
      newParents.addAll(parents);
    }
  }
  
  private void addVocabularyToMember(Element childElement, CompositeMember member) {
    Elements vocabElements = childElement.getChildElements(Spec.VOCABULARY);
    for (int j = 0; j < vocabElements.size(); j++) {
      Element vocabElement = vocabElements.get(j);
      Vocabulary vocab = new Vocabulary();
      Elements vocabValuesElements = vocabElement.getChildElements(Spec.VALUE);
      for (int k = 0; k < vocabValuesElements.size(); k++) {
        vocab.addValue(vocabValuesElements.get(k).getValue());
      }
      Elements dependentFieldElements = vocabElement.getChildElements(Spec.DEPENDENT_FIELD);
      for (int k = 0; k < dependentFieldElements.size();k++) {
        Element dependentFieldElement = dependentFieldElements.get(k);
        String depElementId = stringValue(dependentFieldElement, Spec.ELEMENT_ID);
        String depValue = stringValue(dependentFieldElement, Spec.VALUE);          
        vocab.addDependentField(depElementId, depValue);
      }
      member.addVocabulary(vocab);
    }
  }
  
  private String createTypeName(Element element, boolean includeStudyTypeName) {
    String name = stringValue(element, Spec.TAG);
    name = name.replace("-", "_");    
    
    String parentTag = optionalStringValue(element.getParent(), Spec.TAG);
    
    if ("TestSubstanceCharacterization".equals(parentTag)) {
      name = "TestSubstancePropertyList";
    }
    else if (isControl(element)) {
      if (isStringValue(element.getParent(), Spec.TAG, Spec.POSITIVE_CONTROL_LIST_TAG)) {
        name = "PositiveTestControl";
      }
      else if (isStringValue(element.getParent(), Spec.TAG, Spec.NEGATIVE_CONTROL_LIST_TAG)) {
        name = "NegativeTestControl";
      }
    }
    else if ("ResultFindings".equals(name) && isStringValue(element, Spec.TYPE, Spec.LIST)) {
      name = "ResultFindingsList";
    }
    else if ("Data".equals(name)) {
      if (parentTag.endsWith("Data")) {
        name = parentTag + "Value";
      }
    }
    else if ("ResultFindings".equals(name)) {
      if ("TreatmentResultFindings".equals(parentTag)) {
        name = "GenericResultFindings";
      }
      else if ("TestResultFindings".equals(parentTag)) {
        name = "GenericTestResultFindings";
      }
      else {
        name = parentTag + "ResultFindings";
      }
    }
    else if ("Substance".equals(name) && isStringValue(element, Spec.TYPE, Spec.COMPOSITE)) {
      name = parentTag + "SubstanceQuantity";
    }    
    else if ("OriginalDosageRegimen".equals(name)) {
      name = "DosageRegimen";
    }
    else if (isCompoundRoot(element)) {
      name = "CompoundRecord";
    }
    else if ("Property".equals(name)) {
      String grandparentTag = stringValue(element.getParent().getParent(), Spec.TAG);
      if (grandparentTag.equals("TestSubstanceCharacterization")) {
        name = "TestSubstanceProperty";
      }
      else {
        if (parentTag.endsWith("Properties")) {
          name = parentTag.substring(0, parentTag.length() - "Properties".length()) +
              "Property";
        }
      }
    }
    else if ("InChI".equals(name) && isStringValue(element, Spec.TYPE, Spec.SET)) {
      name = "InChIList";
    }
    else if ("Smiles".equals(name) && isStringValue(element, Spec.TYPE, Spec.SET)) {
      name = "SmilesList";
    }

    if (includeStudyTypeName) {
      String studyTypeName = getStudyTypeName(element);
      if (studyTypeName != null && !name.startsWith(studyTypeName)) {
        return studyTypeName + name;
      }
      else {
        return name;
      }
    }
    else {
      return name;
    }
  }
  
  private ToxmlClass createOrAddType(Element element) {
    String typeName = stringValue(element, Spec.TYPE);    
    ToxmlClass definedClass = typesMap.get(typeName);    
    if (definedClass == null) {
      ToxmlClass createdClass = createClass(element);
      if (createdClass == null) {
        throw new RuntimeException("No defined class for type: " + typeName);
      }
      return createdClass;
    }    
    else {
      return definedClass;
    }
  }
       
  private Map<String, ToxmlClass> createPrimitiveTypeMap() {
    Map<String, ToxmlClass> map = new HashMap<String, ToxmlClass>();
    map.put(Spec.BOOLEAN, new PrimitiveClass("BooleanValue"));
    map.put(Spec.CDATA, new PrimitiveClass("CDataValue"));
    map.put(Spec.DATE, new PrimitiveClass("DateValue"));
    map.put(Spec.FLOAT, new PrimitiveClass("FloatValue"));
    map.put(Spec.INEXACT_VALUE, new PrimitiveClass("InexactValue"));
    map.put(Spec.INTEGER, new PrimitiveClass("IntegerValue"));
    map.put(Spec.INTEGER_RANGE, new PrimitiveClass("IntegerRange"));
    map.put(Spec.INTEGER_ARRAY, new PrimitiveClass("IntegerArray"));
    map.put(Spec.QUANTITY, new PrimitiveClass("Quantity", true));
    map.put(Spec.STRING, new PrimitiveClass("StringValue"));
    map.put(Spec.TYPED_VALUE, new PrimitiveClass("TypedValue"));
    map.put(Spec.UNITS, new PrimitiveClass("Units", true));
    return map;
  }
  
  private ToxmlClass nameCheckAndAddType(Element element, ToxmlClass newClass) {
    String typeName = createTypeName(element, true);
    String studyTypeName = getStudyTypeName(element);
    
    newClass.setName(typeName);
    newClass.setStudyTypeName(studyTypeName);
    newClass.setDescription(optionalStringValue(element, Spec.DESCRIPTION));
    
    ToxmlClass definedClass = typesMap.get(typeName);
    if (definedClass != null) {
      if (definedClass.isEquivalent(newClass)) {
        return definedClass;
      }
      else {
        throw new RuntimeException("Class name collision: " + typeName +
            "\n" + definedClass.toString("Defined", 0) + "\n\n" + newClass.toString("New", 0));
      }
    }

    if (studyTypeName != null) {
      allStudyTypeNames.add(studyTypeName);
    }
    addToTypesMap(newClass);
    System.out.println("Added class: " + newClass);
    
    return newClass;
  }
        
  // node queries
  
  private boolean isCompoundRoot(Node node) {
    return node != null && node.getParent() == rootElement &&
        isStringValue(node, Spec.ROOT_TYPE, Spec.COMPOUND_ROOT_TYPE);
  }
  
  private boolean isTypeDefinition(Node node) {
    return (node instanceof Element) && node.getParent() == rootElement &&
        Spec.TYPE.equals(((Element)node).getLocalName());
  }
  
  private boolean isComplexTypeRoot(Node node) {
    return (node instanceof Element) && isTypeDefinition(node.getParent()) &&
        Spec.ELEMENT.equals(((Element)node).getLocalName());
  }
  
  private boolean isToxicityStudies(Node node) {
    return (node instanceof Element) && isCompoundRoot(node.getParent()) &&
        isStringValue(node, Spec.TAG, Spec.TOXICITY_STUDIES_TAG);
  }
  
  private boolean isStudyList(Node node) {
    return (node instanceof Element) && isToxicityStudies(node.getParent());
  }
  
  private boolean isStudy(Node node) {
    return (node instanceof Element) && isStudyList(node.getParent());
  }
  
  private boolean isTestList(Node node) {
    return (node instanceof Element) && isStudy(node.getParent()) &&
        isStringValue(node, Spec.TAG, Spec.TEST_LIST_TAG);
  }

  private boolean isTest(Node node) {
    return (node instanceof Element) && isTestList(node.getParent());
  }
    
  private boolean isControlList(Node node) {
    return (node instanceof Element) && isTest(node.getParent()) &&
        (isStringValue(node, Spec.TAG, Spec.POSITIVE_CONTROL_LIST_TAG) ||
           isStringValue(node, Spec.TAG, Spec.NEGATIVE_CONTROL_LIST_TAG));
  }

  private boolean isControl(Node node) {
    return (node instanceof Element) && isControlList(node.getParent());
  }
  
  private boolean isTreatmentList(Node node) {
    return (node instanceof Element) && isTest(node.getParent()) &&
        isStringValue(node, Spec.TAG, Spec.TREATMENT_LIST_TAG);
  }
  
  private boolean isControlTreatment(Node node) {
    return (node instanceof Element) && isControl(node.getParent()) &&
        isStringValue(node, Spec.TAG, Spec.TREATMENT_TAG);      
  }
  
  private boolean isTreatment(Element node) {
    return node != null &&
        isControlTreatment(node) || isTreatmentList(node.getParent());            
  }
  
  private boolean hasCompositeChild(Element node) {
    Elements childElements = node.getChildElements();
    for (int i = 0; i < childElements.size(); i++) {
      Element child = childElements.get(i);
      if (isStringValue(child, Spec.TYPE, Spec.COMPOSITE)) {
        return true;
      }
    }
    return false;
  }
  
  private Element getStudyListElement(Node node) {
    if (node == null) {
      return null;
    }
    if (isStudyList(node)) {
      return (Element)node;
    }
    else {
      return getStudyListElement(node.getParent());
    }
  }

  private Element getTreatmentTypeElement(Node node) {
    if (node == null) {
      return null;
    }
    if (isComplexTypeRoot(node)) {
      return (Element)node;
    }
    else {
      return getTreatmentTypeElement(node.getParent());
    }
  }
  
  private String getStudyTypeName(Element node) {
    Element studyListElement = getStudyListElement(node);
    if (studyListElement == null) {
      Element treatmentTypeElement = getTreatmentTypeElement(node);
      if (treatmentTypeElement == null) {
        return null;
      }
      else {
        String treatmentTypeName = stringValue(treatmentTypeElement.getParent(), Spec.NAME);
        if (!treatmentTypeName.endsWith(Spec.STUDY_TREATMENT)) {
          throw new RuntimeException("Treatment type name does not end with " + 
              Spec.STUDY_TREATMENT + ": " + treatmentTypeName);
        }
        return treatmentTypeName.substring(0, treatmentTypeName.length() - Spec.STUDY_TREATMENT.length());
      }
    }
    else {
      String studyListTag = stringValue(studyListElement, Spec.TAG);
      if (!studyListTag.endsWith("Studies")) {
        throw new RuntimeException("Study list tag does not end with Studies: [" + studyListTag + "]");
      }
      return studyListTag.substring(0, studyListTag.length() - "Studies".length());
    }
  }
  
  private boolean booleanValue(Element element, String childTag) {
    return "true".equals(element.getFirstChildElement(childTag).getValue());    
  }
  
  private long longValue(Element element, String childTag) {
    return Long.parseLong(element.getFirstChildElement(childTag).getValue());
  }

  private String stringValue(Node element, String childTag) {
    return ((Element)element).getFirstChildElement(childTag).getValue();
  }
  
  private String optionalStringValue(Node node, String childTag) {
    if (node instanceof Element) {
      Element childElement = ((Element)node).getFirstChildElement(childTag);
      if (childElement == null) {
        return null;
      }
      return childElement.getValue();      
    }
    else {
      return null;
    }
  }

  private boolean isStringValue(Node element, String childTag, String targetValue) {
    String value = optionalStringValue(element, childTag);
    if (value == null) {
      return targetValue == null;
    }
    return value.equals(targetValue);
  }
    
  private void renameStudyTreatmentTypes() {
    for (String studyTypeName : allStudyTypeNames) {
      ToxmlClass treatmentType = typesMap.get(studyTypeName + "StudyTreatment");
      if (treatmentType != null) {
        treatmentType.setName(studyTypeName + "Treatment");
      }
    }
  }
  
  private void updateVocabularyDependentFields() {
    System.out.println("Finding vocabulary dependent fields - may take a moment...");
    for (CompositeMember member : elementIdCompositeMemberMap.values()) {
      String elementId = member.getElementId();
      Element element = elementIdMap.get(elementId);
      for (Vocabulary vocab : member.getVocabularies()) {
        for (DependentField dep : vocab.getDependencies()) {
          String depElementId = dep.getElementId();
          Element depElement = elementIdMap.get(depElementId);
          if (depElement == null) {
            throw new RuntimeException("Never found dependent field element for: " + depElementId + 
              " for member: " + member.getTag() + " " + member.getType());
          }
          String relativePath = computeRelativePath(element, depElement, new HashSet<Element>());
          dep.setRelativePath(relativePath);
        }
      }
    }
  }
  
  // return null if a path could not be found
  private String computeRelativePath(Element from, Element to, Set<Element> visitedNodes) {
    if (from == null) {
      return null;
    }
    if (visitedNodes.contains(from)) {
      return null;
    }
    if (from == to) {
      return "";
    }
    
    visitedNodes.add(from);
    
    Elements childElements = from.getChildElements();
    for (int i = 0; i < childElements.size(); i++) {
      Element childElement = childElements.get(i);
      String childSearch = computeRelativePath(childElement, to, visitedNodes);
      if (childSearch != null) {
        return join("/", stringValue(to, Spec.TAG), childSearch);
      }
    }

    if (from.getParent() instanceof Element) {
      String parentSearch = computeRelativePath((Element)from.getParent(), to, visitedNodes);
      if (parentSearch != null) {     
        return join("/", "..", parentSearch);
      }
    }
        
    return null;
  }
  
  private void mergeEquivalentTypes() {
    System.out.println("Merging equivalent types - may take a moment...");
    
    // these will no longer be valid after merging types
    elementIdMap = null;
    elementIdCompositeMemberMap = null;
      
    Set<String> consideredTypeNames = new HashSet<String>();
    List<ToxmlClass> allClasses = new ArrayList<ToxmlClass>(typesMap.values());
    
    for (ToxmlClass type : allClasses) {
      String typeName = type.getName();
      String studyTypeName = type.getStudyTypeName();
      if (studyTypeName != null) {
        String targetTypeName = typeName.substring(studyTypeName.length(), typeName.length());
        if (!consideredTypeNames.contains(targetTypeName)) {
          consideredTypeNames.add(targetTypeName);
          ToxmlClass oldGenericType = typesMap.get(targetTypeName);
          if (oldGenericType == null || oldGenericType.isEquivalent(type)) {
            boolean allEquivalent = true;
            Set<String> otherStudyTypeNames = new HashSet<String>();
            for (String nextStudyTypeName : allStudyTypeNames) {
              ToxmlClass otherType = typesMap.get(nextStudyTypeName + targetTypeName);
              if (otherType != null && otherType != type) {
                if (!otherType.isEquivalent(type)) {
                  System.out.println(type.getName() + " is not equivalent to generic " + otherType.getName());
                  allEquivalent = false;
                  break;
                }
                otherStudyTypeNames.add(nextStudyTypeName);
              }
            }
            if (allEquivalent && (oldGenericType != null || otherStudyTypeNames.size() > 0)) {
              mergeToGenericType(targetTypeName);
            }
          }
          else {
            System.out.println(type.getName() + " is not equivalent to generic " + oldGenericType.getName());
          }
        }
      }
    }
  }
    
  private void mergeToGenericType(String targetTypeName) {
    System.out.println("Merging to generic type: " + targetTypeName);
    ToxmlClass genericType = typesMap.get(targetTypeName);
    for (String studyTypeName : allStudyTypeNames) {
      String oldStudyTypeName = studyTypeName + targetTypeName;
      ToxmlClass oldStudyType = typesMap.get(oldStudyTypeName);
      if (oldStudyType != null) {
        if (genericType == null) {
          genericType = oldStudyType;
          genericType.setName(targetTypeName);
          genericType.setStudyTypeName(null);
          addToTypesMap(genericType);
        }
        System.out.println("  Moved " + oldStudyTypeName);
        updateChildReferences(oldStudyTypeName, genericType);
        typesMap.remove(oldStudyTypeName);
      }
    }
  }
  
  private String join(String delimiter, String... strings) {
    StringBuilder sb = new StringBuilder();
    for (String s : strings) {
      if (s != null) {
        s = s.trim();
        if (s.length() > 0) {
          if (sb.length() > 0) {
            sb.append(delimiter);
          }
          sb.append(s);
        }
      }
    }
    return sb.toString();
  }
}

/**
 * Stucco ToxML API - a Java interface for parsing and creating ToxML documents
 * Copyright (C) 2016 Scott Miller - Leadscope, Inc.
 *
 * Leadscope, Inc. licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.leadscope.stucco.builder;

import java.util.*;

/**
 * Member of a CompositeClass
 */
public class CompositeMember implements ToxmlClassParent {
  private String tag;
  private String description;
  private ToxmlClass type;
  private NavigableSet<String> preferredUnits = new TreeSet<String>();
  private String elementId;
  private List<Vocabulary> vocabularies = new ArrayList<Vocabulary>();
  
  public CompositeMember(String tag, ToxmlClass type) {
    this.tag = tag;
    this.type = type;
  }
  
  public boolean isEquivalent(CompositeMember other) {
    if (!tag.equals(other.getTag())) {
      return false;
    }    
    if (description == null) {
      if (other.description != null) {
        return false;
      }
    }
    else if (!description.equals(other.description)) {
      return false;
    }    
    if (!type.isEquivalent(other.type)) {
      return false;
    }
    if (preferredUnits.size() != other.preferredUnits.size()) {
      return false;
    }
    for (String unit : preferredUnits) {
      if (!other.preferredUnits.contains(unit)) {
        return false;
      }
    }
    if (vocabularies.size() != other.vocabularies.size()) {
      return false;
    }
    for (int i = 0; i < vocabularies.size(); i++) {
      if (!vocabularies.get(i).isEquivalent(other.vocabularies.get(i))) {
        return false;
      }
    }
        
    return true;
  }
  
  public void setChildClass(ToxmlClass type) {
    setType(type);
  }
  
  public ToxmlClass getChildClass() {
    return getType();
  }
  
  public ToxmlClass getType() {
    return type;
  }
  
  public void setType(ToxmlClass type) {
    this.type = type;
  }
  
  public String getTag() {
    return tag;
  }
  
  public String getJavaSafeTag() {
    return tag.replace('-', '_');
  }

  public String getCamelTag() {
    return ToxmlClass.camelCase(tag);
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public String getDescription() {
    return description;
  }
  
  public boolean isHasPreferredUnits() {
    return preferredUnits.size() > 0;
  }
  
  public void addPreferredUnits(String units) {
    preferredUnits.add(units);
  }
  
  public List<String> getPreferredUnits() {
    return new ArrayList<String>(preferredUnits);
  }

  public boolean isBoolean() {
    return type.getName().equals("BooleanValue");
  }
  
  public boolean isString() {
    return type.getName().equals("StringValue");
  }
  
  public boolean isCdata() {
    return type.getName().equals("CDataValue");
  }
  
  public boolean isFloat() {
    return type.getName().equals("FloatValue");
  }
  
  public boolean isInteger() {
    return type.getName().equals("IntegerValue");
  }
  
  public boolean isCollection() {
    return type instanceof CollectionClass;
  }

  public boolean isCollectionOrComposite() {
    return type instanceof CollectionClass ||
        type instanceof CompositeClass;
  }
  
  public String getCommentString() {
    if (description == null) {
      return null;
    }
    else {      
      String escapedString = description.replaceAll("\\*\\/", "");       
      String nextEscape = escapedString.replaceAll("\\*\\/", "");
      while (!nextEscape.equals(escapedString)) {
        escapedString = nextEscape;
        nextEscape = escapedString.replaceAll("\\*\\/", "");
      }
      
      StringBuilder sb = new StringBuilder();
      for (String line : escapedString.split("\n")) {
        line = line.trim();
        if (line.length() > 0) {
          if (sb.length() > 0) {
            sb.append(" * ");
          }
          sb.append(line);
          sb.append("\n");
        }
      }
      return escapedString;      
    }
  }
  
  public CompositeMember copy() {
    CompositeMember newMember = new CompositeMember(tag, type);
    newMember.description = description;
    for (String units : preferredUnits) {
      newMember.addPreferredUnits(units);
    }
    return newMember;
  }

  public String getElementId() {
    return elementId;
  }

  public void setElementId(String elementId) {
    this.elementId = elementId;
  }
  
  public void addVocabulary(Vocabulary vocab) {
    vocabularies.add(vocab);
  }
  
  public List<Vocabulary> getVocabularies() { 
    return vocabularies;
  }
  
  public boolean isHasVocabulary() {
    return vocabularies.size() > 0;
  }
  
  public List<Vocabulary> getDependencyVocabularies() {
    List<Vocabulary> depVocabs = new ArrayList<Vocabulary>();
    for (Vocabulary vocab : vocabularies) {
      if (vocab.isHasDependencies()) {
        depVocabs.add(vocab);
      }
    }
    return depVocabs;
  }
  
  public Vocabulary getNonDependencyVocabulary() {
    for (Vocabulary vocab : vocabularies) {
      if (!vocab.isHasDependencies()) {
        return vocab;
      }
    }
    return null;
  }
  
  public boolean isHasOnlyDependentVocabularies(){
    if (vocabularies.size() == 0) {
      return false;
    }
    for (Vocabulary vocab : vocabularies) {
      if (!vocab.isHasDependencies()) {
        return false;
      }
    }
    return true;
  }
}

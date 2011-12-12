/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Member of a CompositeClass
 */
public class CompositeMember {
  private String tag;
  private String description;
  private ToxmlClass type;
  private List<String> preferredUnits = new ArrayList<String>();
  private String elementId;
  private List<Vocabulary> vocabularies = new ArrayList<Vocabulary>();
  
  public CompositeMember(String tag, ToxmlClass type) {
    this.tag = tag;
    this.type = type;
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
  
  public void addPreferredUnits(String units) {
    preferredUnits.add(units);
  }
  
  public List<String> getPreferredUnits() {
    return preferredUnits;
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

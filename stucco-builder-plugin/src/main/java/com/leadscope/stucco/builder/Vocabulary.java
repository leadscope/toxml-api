/**
 * Copyright 2011 - brokenmodel.com
 * All rights reserved.
 */
package com.leadscope.stucco.builder;

import java.util.*;

/**
 * A vocabulary for a composite member
 */
public class Vocabulary {
  private NavigableSet<String> values = new TreeSet<String>();
  private List<DependentField> dependencies = new ArrayList<DependentField>();
  
  public boolean isEquivalent(Vocabulary other) {
    if (values.size() != other.values.size()) {
      return false;
    }
    for (String value : values) {
      if (!other.values.contains(value)) {
        return false;
      }
    }
    if (dependencies.size() != other.dependencies.size()) {
      for (int i = 0; i < dependencies.size(); i++) {
        if (!dependencies.get(i).isEquivalent(other.dependencies.get(i))) {
          return false;
        }
      }
    }
    
    return true;
  }
  
  public void addValue(String value) { 
    values.add(value);
  }
  
  public void addDependentField(String elementId, String value) {
    DependentField dependency = new DependentField();
    dependency.setElementId(elementId);
    dependency.setValue(value);
    dependencies.add(dependency);
  }  
  
  public List<String> getValues() {
    return new ArrayList<String>(values);
  }
  
  public List<DependentField> getDependencies() {
    return dependencies;
  }
  
  public boolean isHasDependencies() {
    return dependencies.size() > 0;
  }
}

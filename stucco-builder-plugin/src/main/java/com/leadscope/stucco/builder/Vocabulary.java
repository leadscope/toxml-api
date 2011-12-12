/**
 * Copyright 2011 - brokenmodel.com
 * All rights reserved.
 */
package com.leadscope.stucco.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * A vocabulary for a composite member
 */
public class Vocabulary {
  private List<String> values = new ArrayList<String>();
  private List<DependentField> dependencies = new ArrayList<DependentField>();
  
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
    return values;
  }
  
  public List<DependentField> getDependencies() {
    return dependencies;
  }
  
  public boolean isHasDependencies() {
    return dependencies.size() > 0;
  }
}

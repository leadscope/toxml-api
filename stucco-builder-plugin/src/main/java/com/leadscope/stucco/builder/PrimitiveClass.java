/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.antlr.stringtemplate.StringTemplate;

public class PrimitiveClass extends ToxmlClass {
  public PrimitiveClass(String name) {
    setName(name);
  }
  
  public PrimitiveClass(String name, boolean hasUnits) {
    setName(name);
    setHasUnits(hasUnits);
  }  
  
  public boolean isUsingUtilPackage() {
    return false;
  }
  
  public boolean isUsingModelPackage() {
    return false;
  }

  public boolean equivalent(ToxmlClass other) {
    if (!getClass().equals(other.getClass())) {
      return false;
    }
    
    PrimitiveClass otherPrimitive = (PrimitiveClass)other;
    return getName().equals(other.getName()) &&
        isHasUnits() == otherPrimitive.isHasUnits();
  }
  
  public List<String> getChildTypeNames() {
    return new ArrayList<String>(0);
  }

  public ToxmlClass copy(String newName) {
    throw new RuntimeException("Cannot copy primitive classes");
  }
  
  public void generateTemplate(File srcDir) { 
    // no template is generated
  }
  
  public void fillInTemplate(StringTemplate st) {
    throw new RuntimeException("Primitive classes should never be generated");
  }
}

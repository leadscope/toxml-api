/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.builder;

import java.util.Arrays;
import java.util.List;

import org.antlr.stringtemplate.StringTemplate;

public abstract class CollectionClass extends ToxmlClass {
  private String childTag;
  private ToxmlClass childClass;
  
  public String getChildTag() {
    return childTag;
  }
  
  public void setChildTag(String childTag) {
    this.childTag = childTag;
  }
  
  public ToxmlClass getChildClass() {
    return childClass;
  }
  
  public void setChildClass(ToxmlClass childClass) {
    this.childClass = childClass;
  }
  
  public boolean isUsingUtilPackage() {
    return false;
  }
  
  public List<String> getChildTypeNames() {
    return Arrays.asList(childClass.getName());
  }
  
  public boolean isUsingModelPackage() {
    return getPackageName() == null || childClass.getPackageName() == null  
          && !(childClass instanceof PrimitiveClass);
  }
  
  public void fillInTemplate(StringTemplate st) {
    st.setAttribute("childTag", childTag);
    st.setAttribute("childClass", childClass.getName());
  }
  
  public String toString(String tag, int indent) {
    StringBuilder sb = new StringBuilder();
    sb.append(super.toString(tag, indent));
    sb.append("\n");
    sb.append(childClass.toString(childTag, indent+1));
    return sb.toString();
  }
  
  public CollectionClass copy(String newName) {
    try {
      CollectionClass newClass = getClass().newInstance();
      newClass.setName(newName);
      newClass.setChildTag(getChildTag());
      newClass.setChildClass(getChildClass());
      return newClass;
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  public boolean equivalent(ToxmlClass other) {
    if (!getClass().equals(other.getClass())) {
      return false;
    }
    
    CollectionClass otherCollection = (CollectionClass)other;
    if (!childTag.equals(otherCollection.getChildTag())) {
      return false;
    }
    
    return childClass.equivalent(otherCollection.childClass);
  }
  
  public void changeChildType(String oldTypeName, ToxmlClass newClass) {
    childClass = newClass;
  }
}

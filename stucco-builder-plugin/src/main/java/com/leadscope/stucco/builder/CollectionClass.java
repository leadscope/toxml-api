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

import java.util.Arrays;
import java.util.List;

import org.antlr.stringtemplate.StringTemplate;

public abstract class CollectionClass extends ToxmlClass implements ToxmlClassParent {
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
  
  public boolean isEquivalent(ToxmlClass other) {
    if (!getClass().equals(other.getClass())) {
      return false;
    }
    
    CollectionClass otherCollection = (CollectionClass)other;
    if (!childTag.equals(otherCollection.getChildTag())) {
      return false;
    }
    
    return childClass.isEquivalent(otherCollection.childClass);
  }
  
  public void changeChildType(String oldTypeName, ToxmlClass newClass) {
    childClass = newClass;
  }
}

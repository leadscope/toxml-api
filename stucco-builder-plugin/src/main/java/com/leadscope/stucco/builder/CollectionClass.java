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

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

import org.antlr.stringtemplate.StringTemplate;

public class EnumeratedTypeClass extends ToxmlClass {
  private Set<String> values = new HashSet<String>();
  private String description;
  
  public List<String> getChildTypeNames() {
    return new ArrayList<String>(0);
  }
  
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void addValue(String value) {
    values.add(value);
  }
  
  public List<String> getValues() {
    List<String> valueList = new ArrayList<String>(values);
    Collections.sort(valueList);
    return valueList;
  }

  public boolean isUsingUtilPackage() {
    return true;
  }
  
  public boolean isUsingModelPackage() {
    return getPackageName() == null;
  }
  
  public void fillInTemplate(StringTemplate st) {
    st.setAttribute("values", values);
  }

  public boolean isEquivalent(ToxmlClass other) {
    if (!getClass().equals(other.getClass())) {
      return false;
    }
    
    EnumeratedTypeClass otherType = (EnumeratedTypeClass)other;
    List<String> myValues = getValues();
    List<String> otherValues = otherType.getValues();
    if (myValues.size() != otherValues.size()) {
      return false;
    }
    for (int i = 0; i < myValues.size(); i++) {
      String myValue = myValues.get(i);
      String otherValue = otherValues.get(i);
      if (!myValue.equals(otherValue)) {
        return false;
      }
    }
    return true;
  }

  public EnumeratedTypeClass copy(String newName) {
    EnumeratedTypeClass newClass = new EnumeratedTypeClass();
    newClass.setName(newName);
    newClass.setDescription(getDescription());
    for (String value : getValues()) {
      newClass.addValue(value);
    }
    return newClass;
  }  
}

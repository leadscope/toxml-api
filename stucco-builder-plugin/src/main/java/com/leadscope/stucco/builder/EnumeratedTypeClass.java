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

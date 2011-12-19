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

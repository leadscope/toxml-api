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

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

  public boolean isEquivalent(ToxmlClass other) {
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

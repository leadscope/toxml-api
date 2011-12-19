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

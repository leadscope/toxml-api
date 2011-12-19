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

/**
 * Field dependency for a vocabulary
 */
public class DependentField {
  private String elementId;
  private String value;
  private String relativePath;
  
  public boolean isEquivalent(DependentField other) {
    return value.equals(other.value) && relativePath.equals(other.relativePath);
  }
  
  public String getElementId() {
    return elementId;
  }
  public void setElementId(String elementId) {
    this.elementId = elementId;
  }
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }
  public String getRelativePath() {
    return relativePath;
  }
  public void setRelativePath(String relativePath) {
    this.relativePath = relativePath;
  }  
  public String getRelativePathToParent() {
    if (relativePath == null) {
      return null;
    }
    if (relativePath.startsWith("../")) {
      return relativePath.substring(3);
    }
    else {
      throw new RuntimeException("Cannot determine relative path to parent - does not start with ..");
    }
  }
  
}

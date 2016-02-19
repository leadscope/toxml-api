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

/**
 * Copyright 2011 - brokenmodel.com
 * All rights reserved.
 */
package com.leadscope.stucco.builder;

/**
 * Field dependency for a vocabulary
 */
public class DependentField {
  private String elementId;
  private String value;
  private String relativePath;
  
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

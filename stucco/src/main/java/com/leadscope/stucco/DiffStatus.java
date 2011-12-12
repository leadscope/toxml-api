/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

/**
 * Indicates the status of an object in regards to the difference
 * of two different lsml documents
 */
public enum DiffStatus {
  ADDED("added"),
  REMOVED("removed"),
  CONFLICT("conflict");
  
  private String value;
  private DiffStatus(String value) {
    this.value = value;
  }
  
  public String toString() {
    return value;
  }
  
  public static DiffStatus parse(String s) {
    if (s == null) {
      return null;
    }
    s = s.trim().toLowerCase();
    if (s.length() == 0) {
      return null;
    }
    
    for (DiffStatus status : DiffStatus.values()) {
      if (status.value.equals(s)) {
        return status;
      }
    }
    
    throw new RuntimeException("Unknown difference status: " + s);
  }
}

/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

import com.leadscope.stucco.util.Displayable;

/**
 * Toxml value representing a simple string that is stored in the xml as a CDATA block
 */
public class CDataValue extends AbstractPrimitiveToxmlObject 
   implements Displayable, HashableToxmlObject, Comparable<CDataValue> {
  
  private String value;
  
  public CDataValue() { }
  
  public CDataValue(String value) {
    setValue(value);
  }
  
  public <V> V accept(ToxmlVisitor<V> visitor) throws Exception {
    return visitor.visit(this);
  }
  
  public String getValue() {
    return value;
  }
  
  /**
   * Sets the value for the string
   * @param value should never be null
   */
  public void setValue(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Should not have null value - use null instead of " + getClass().getName());
    }
    this.value = value.trim();
  }
  
  public String toString() {
    return value == null ? "null" : value;
  }
  
  public String toDisplayableString() {
    return value == null ? "null" : value;
  }
  
  public int compareTo(CDataValue other) {
    return value.compareTo(other.value);
  }
  
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (!getClass().equals(other.getClass())) {
      return false;
    }
    return value.equals(((CDataValue)other).value);
  }
  
  public int hashCode() {
    return value.hashCode();
  }
}

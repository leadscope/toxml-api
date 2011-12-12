/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

import java.util.List;

import com.leadscope.stucco.util.Displayable;

/**
 * A string value that must be one of a pre-specified list of values. Note - this is NOT the
 * same paradigm as an enum in java. The same value might appear in different objects - this
 * is inherently required since different values might have different source attributes. 
 */
public abstract class AbstractToxmlEnumeratedType extends AbstractPrimitiveToxmlObject 
  implements Displayable, HashableToxmlObject {
  private String value;
  
  /**
   * Gets all of the pre-specified values that are allowed
   * @return the list of all values
   */
  public abstract List<String> getAllValues();
    
  /**
   * Sets the value of this type
   * @param value the value of the type
   */
  public void setValue(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Should not have null value - use null instead of " + getClass().getName());
    }
    value = value.trim();
    for (String typeValue : getAllValues()) {
      if (typeValue.equalsIgnoreCase(value)) {
        this.value = typeValue;
        return;
      }
    }
    throw new RuntimeException(value + " is not a valid value for this type: " + getClass().getName());
  }
  
  public String getValue() {
    return value;
  }
  
  public <V> V accept(ToxmlVisitor<V> visitor) throws Exception {
    return visitor.visit(this);
  }
  
  public String toString() {
    return value == null ? "null" : value;
  }
  
  public String toDisplayableString() {
    return value == null ? "null" : value;
  }
 
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (!getClass().equals(other.getClass())) {
      return false;
    }
    return value.equals(((AbstractToxmlEnumeratedType)other).value);
  }

  public int hashCode() {
    return value.hashCode();
  }
}

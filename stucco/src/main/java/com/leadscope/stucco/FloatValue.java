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
package com.leadscope.stucco;

import com.leadscope.stucco.util.Displayable;
import com.leadscope.stucco.util.StringUtil;

/**
 * Toxml value representing a float value
 */
public class FloatValue extends AbstractPrimitiveToxmlObject 
     implements Displayable, HashableToxmlObject, Comparable<FloatValue> {
  
  private Float value;
  
  public FloatValue() { }
  
  public FloatValue(Float value) {
    setValue(value);
  }
  
  public <V> V accept(ToxmlVisitor<V> visitor) throws Exception {
    return visitor.visit(this);
  }
  
  public Float getValue() {
    return value;
  }
  
  /**
   * Sets the value for the given string
   * @param value should never be null
   */  
  public void setValue(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Should not have null value - use null instead of " + getClass().getName());
    }
    this.value = Float.parseFloat(value);
  }
  
  /**
   * Sets the value for the float
   * @param value should never be null
   */  
  public void setValue(Float value) {
    if (value == null) {
      throw new IllegalArgumentException("Should not have null value - use null instead of " + getClass().getName());
    }
    this.value = value;
  }  
  
  public String toString() {
    return value == null ? "null" : StringUtil.formatProperty(value);
  }
  
  public String toDisplayableString() {
    return toString();
  }
  
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (!getClass().equals(other.getClass())) {
      return false;
    }
    return value.equals(((FloatValue)other).value);
  }
  
  public int compareTo(FloatValue other) {
    return value.compareTo(other.value);
  }
  
  public int hashCode() {
    return value.hashCode();
  }
}

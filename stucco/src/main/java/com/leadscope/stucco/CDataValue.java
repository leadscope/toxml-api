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

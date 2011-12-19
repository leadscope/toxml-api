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
 * A string value that has an associated type.
 */
public class TypedValue extends AbstractPrimitiveToxmlObject 
    implements Displayable, HashableToxmlObject, Comparable<TypedValue> {
  
  private String value;
  private String type;
  
  public TypedValue() { }
  
  public TypedValue(String type, String value) {
    setType(type);
    setValue(value);
  }
    
  public String getType() {
    return type;
  }
  
  public String getValue() {
    return value;
  }
  
  /**
   * Sets the value for the typed value
   * @param value should never be null
   */
  public void setValue(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Should not have null value - use null instead of " + getClass().getName());
    }
    this.value = value.trim();
  }

  /**
   * Sets the type for the value
   * @param type should never be null
   */
  public void setType(String type) {
    if (type == null) {
      throw new IllegalArgumentException("Should not have null type");
    }
    this.type = type.trim();
  }

  public String toString() {
    return type == null ? "null" : type + ":" + value == null ? "null" : value;
  }
  
  public String toDisplayableString() {
    return toString();
  }
  
  public int compareTo(TypedValue other) {
    int result = type.compareTo(other.type);
    if (result == 0) {
      result = value.compareTo(other.value);
    }
    return result;
  }

  public int hashCode() {
    final int prime = 31;
    int result = 17;
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TypedValue other = (TypedValue) obj;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    if (value == null) {
      if (other.value != null)
        return false;
    } else if (!value.equals(other.value))
      return false;
    return true;
  }

  public <V> V accept(ToxmlVisitor<V> visitor) throws Exception {
    return visitor.visit(this);
  }
}

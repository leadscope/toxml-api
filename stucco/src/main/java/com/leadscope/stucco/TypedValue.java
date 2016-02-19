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

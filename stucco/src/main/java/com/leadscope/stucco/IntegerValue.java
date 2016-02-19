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
 * Toxml value representing a float value
 */
public class IntegerValue extends AbstractPrimitiveToxmlObject 
    implements Displayable, HashableToxmlObject, Comparable<IntegerValue> {
  
  private Integer value;
  
  public IntegerValue() { }
  
  public IntegerValue(Integer value) {
    setValue(value);
  }
  
  public <V> V accept(ToxmlVisitor<V> visitor) throws Exception {
    return visitor.visit(this);
  }
  
  public Integer getValue() {
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
    this.value = Integer.parseInt(value);
  }
  
  /**
   * Sets the value for the float
   * @param value should never be null
   */  
  public void setValue(Integer value) {
    if (value == null) {
      throw new IllegalArgumentException("Should not have null value - use null instead of " + getClass().getName());
    }
    this.value = value;
  }  
  
  public String toString() {
    return String.valueOf(value);
  }
  
  public String toDisplayableString() {
    return String.valueOf(value);
  }
  
  public int compareTo(IntegerValue other) {
    return value.compareTo(other.value);
  }

  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (!getClass().equals(other.getClass())) {
      return false;
    }
    return value.equals(((IntegerValue)other).value);
  }
  
  public int hashCode() {
    return value.hashCode();
  }
}

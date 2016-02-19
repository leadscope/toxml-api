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
 * Toxml value representing a boolean
 */
public class BooleanValue extends AbstractPrimitiveToxmlObject 
   implements Displayable, HashableToxmlObject, Comparable<BooleanValue> {
  
  private Boolean value;
  
  public BooleanValue() { }
  
  public BooleanValue(boolean value) {
    setValue(value);
  }
    
  public <V> V accept(ToxmlVisitor<V> visitor) throws Exception {
    return visitor.visit(this);
  }
  
  public boolean getValue() {
    return value;
  }
  
  public void setValue(boolean value) {
    this.value = value;
  }
  
  /**
   * Parses the string value - one of true, false, yes, or no
   * @param value
   */
  public void setValue(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Should not have null value - use null instead of " + getClass().getName());
    }
    value = value.trim().toLowerCase();
    if (value.equals("true")) {
      this.value = true;
    }
    else if (value.equals("false")) {
      this.value = false;
    }
    else if (value.equals("yes")) {
      this.value = true;
    }
    else if (value.equals("no")) {
      this.value = false;
    }
    else if (value.equals("1")) {
      this.value = true;
    }
    else if (value.equals("0")) {
      this.value = false;
    }
    else {
      throw new IllegalArgumentException("Unknown boolean value: " + value);
    }
  }
  
  public String toYesNoString() {
    if (value) {
      return "Yes";      
    }
    else {
      return "No";
    }
  }
  
  public String toString() {
    if (value) {
      return "true";
    }
    else {
      return "false";
    }
  }
  
  public int compareTo(BooleanValue other) {
    return value.compareTo(other.value);
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
    return value.equals(((BooleanValue)other).value);
  }
  
  public int hashCode() {
    return value.hashCode();
  }
}

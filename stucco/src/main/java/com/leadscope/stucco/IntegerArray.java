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

import java.util.*;

import com.leadscope.stucco.util.Displayable;

/**
 * Toxml value representing a float value
 */
public class IntegerArray extends AbstractPrimitiveToxmlObject 
    implements Displayable, HashableToxmlObject {
  
  private int[] values;
  
  public IntegerArray() { }
  
  public IntegerArray(int[] values) {
    setValues(values);
  }
  
  public <V> V accept(ToxmlVisitor<V> visitor) throws Exception {
    return visitor.visit(this);
  }
  
  public int[] getValues() {
    return values;
  }
  
  /**
   * Sets the value for the given string
   * @param value should never be null
   */  
  public void setValue(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Should not have null value - use null instead of " + getClass().getName());
    }
    StringTokenizer st = new StringTokenizer(value, ",");
    List<Integer> intValues = new ArrayList<Integer>();
    while (st.hasMoreTokens()) {
      intValues.add(new Integer(Integer.parseInt(st.nextToken())));
    }
    values = new int[intValues.size()];
    for (int i = 0; i < values.length; i++) {
      values[i] = intValues.get(i).intValue();
    }
  }
  
  /**
   * Sets the value for the float
   * @param value should never be null
   */  
  public void setValues(int[] values) {
    if (values == null) {
      throw new IllegalArgumentException("Should not have null values - use null instead of " + getClass().getName());
    }
    this.values = values;
  }  
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < values.length; i++) {
      if (i > 0) {
        sb.append(",");
      }
      sb.append(values[i]);
    }
    return sb.toString();
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
    
    int[] otherValues = ((IntegerArray)other).getValues();
    
    if (otherValues.length != values.length) {
      return false;
    }
    for (int i = 0; i < otherValues.length; i++) {
      if (otherValues[i] != values[i]) {
        return false;
      }
    }
    return true;
  }
  
  public int hashCode() {
    int prime = 31;
    int result = 17;
    for (int i = 0; i < values.length; i++) {
      result = prime * result + values[i];
    }
    return result;
  }
}

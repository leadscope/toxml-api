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

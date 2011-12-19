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
 * A float value that can optionally be specified as a range
 */
public class IntegerRange extends AbstractPrimitiveToxmlObject 
    implements HashableToxmlObject, Displayable, Comparable<IntegerRange> {
  
  private int intValue;
  private int lowValue;
  private int highValue;
  private float comparableValue;
  private boolean isRange;
  
  /**
   * Convenience factory that parses strings like "99", "72 - 83", etc.
   * @param string the string to parse
   */
  public static IntegerRange parse(String string) {
    if (string == null) {
      return null;
    }
    
    String trimmed = string.trim();
    if (trimmed.length() == 0) {
      return null;
    }

    int dashIdx = trimmed.indexOf("-");
    if (dashIdx > 0) {
      String low = trimmed.substring(0, dashIdx).trim();
      String high = trimmed.substring(dashIdx + 1, trimmed.length()).trim();
      int lowValue;
      int highValue;

      if (low.length() > 0) {
        lowValue = Integer.parseInt(low);

        if (high.length() < 1) {
          throw new IllegalArgumentException("Error parsing int range, no high value: " + string);
        }
        else {
          highValue = Integer.parseInt(high);
        }
        
        return new IntegerRange(lowValue, highValue);
      }
    }

    int value = Integer.parseInt(trimmed);
    return new IntegerRange(value);
  }

  public IntegerRange() { }
  
  /**
   * @param intValue the value for this object
   * @exception IllegalArgumentException if floatValue is NaN
   */
  public IntegerRange(int intValue) {
    setValue(intValue);
  }
  
  /**
   * Defines the value as a range.
   * @param lowValue the low value for this object (inclusive)
   * @param highValue the high value for this object (inclusive)
   * @exception IllegalArgumentException if highValue is less than low value
   */
  public IntegerRange(int lowValue, int highValue) {
    setValue(lowValue, highValue);
  }
  
  /**
   * Sets the value as an exact integer
   * @param intValue
   */
  public void setValue(int intValue) {
    this.intValue = intValue;
    this.lowValue = intValue;
    this.highValue = intValue;
    this.comparableValue = intValue;
  }
  
  /**
   * Sets the value as range
   * @param lowValue the low value (inclusive)
   * @param highValue the high value (inclusive)
   * @IllegalARgumentException if highValue is less than lowValue
   */
  public void setValue(int lowValue, int highValue) {
    if (highValue < lowValue) {
      throw new IllegalArgumentException(lowValue + " is greater than " + highValue);
    }

    intValue = lowValue;
    this.lowValue = lowValue;
    this.highValue = highValue;
        
    if (lowValue == highValue) {
      comparableValue = lowValue;
      isRange = false;
    }
    else {
      isRange = true;
      comparableValue = computeComparableValue(lowValue, highValue);
    }
  }
  
  /**
   * Computes the comparable value given the low and high values
   * @param lowValue the low value for this object
   * @param highValue the high value for this object
   * @return the value that should be used for comparisons
   */
  private float computeComparableValue(int lowValue, int highValue) {
    return (float)lowValue + (((float)(highValue - lowValue)) / 2f);
  }

  /**
   * Determines whether or not this is an inexact value.  If
   * this method returns <code>false</code>, the <code>getFloatValue</code>
   * method should be used to get the actual value.  If this method
   * returns <code>true</code>, this object must be treated as a range;
   * use <code>getLowValue</code> and <code>getHighValue</code>.  Note
   * that either of these values (or both) could be infinite.
   */
  public boolean isRange() {
    return isRange;
  }

  /**
   * Gets the float value.  If <code>isRange</code> is false, then this value 
   * is the actual value, otherwise it's the low value
   * @return the central or actual float
   */
  public int getIntValue() {
    return intValue;
  }

  /**
   * Gets the lowest possible value for this object.
   * @return the lowest possible value
   */
  public int getLowValue() {
    return lowValue;
  }

  /**
   * Gets the highest possible value for this object
   * @return the highest possible value
   */
  public int getHighValue() {
    return highValue;
  }

  /**
   * Gets a single value that can be used for comparisons
   * @return a comparable value
   */
  public float getComparableValue() {
    return comparableValue;
  }

  /**
   * Adds another inexact value to this one and returns the result
   * @param v the other value
   * @return the sum of this value and v
   */
  public IntegerRange add(IntegerRange v) {
    if (isRange()) {
      if (v.isRange()) {
        return new IntegerRange(lowValue + v.getLowValue(), highValue + v.getHighValue());
      }
      else {
        return new IntegerRange(lowValue + v.getIntValue(), highValue + v.getIntValue());
      }
    }
    else {
      if (v.isRange()) {
        return new IntegerRange(intValue + v.getLowValue(), intValue + v.getHighValue());
      }
      else {
        return new IntegerRange(intValue + v.getIntValue());
      }
    }
  }

  public int hashCode() {
    final int prime = 31;
    int result = 17;
    result = prime * result + highValue;
    result = prime * result + intValue;
    result = prime * result + (isRange ? 1231 : 1237);
    result = prime * result + lowValue;
    return result;
  }

  public boolean isZero() {
    return lowValue == 0 && highValue == 0;
  }
  
  public boolean equals(Object obj) {
    if (obj == null || !obj.getClass().equals(getClass())) {
      return false;
    }
    IntegerRange iv = (IntegerRange)obj;

    if (isRange != iv.isRange) {
      return false;
    }
    
    if (isRange) {
      if (Float.isNaN(lowValue)) {
        if (!Float.isNaN(iv.lowValue)) {
          return false;
        }
      }
      else if (Float.isNaN(iv.lowValue)) {
        return false;
      }
      else if (lowValue != iv.lowValue) {
        return false;
      }

      if (Float.isNaN(highValue)) {
        if (!Float.isNaN(iv.highValue)) {
          return false;
        }
        else {
          return true;
        }
      }
      else if (Float.isNaN(iv.highValue)) {
        return false;
      }
      else {
        return highValue == iv.highValue;
      }
    }
    else {
      return intValue == iv.intValue;
    }
  }

  /**
   * @param other the object to compare with this one
   * @return 1 if this object is greater than o, 0 if they are equal,
   * and -1 if this object is less than o
   * @exception ClassCastException if o is not of type TypedValue
   */
  public int compareTo(IntegerRange other) {
    if (other.comparableValue < comparableValue) {
      return 1;
    }
    else if (other.comparableValue > comparableValue) {
      return -1;
    }
    else if (other.lowValue < lowValue) {
      return 1;
    }
    else if (other.lowValue > lowValue) {
      return -1;
    }
    else if (other.highValue < highValue) {
      return 1;
    }
    else if (other.highValue > highValue) {
      return -1;
    }
    else {
      return 0;
    }    
  }

  /**
   * Creates a readable version of the inexact value; can be parsed with <code>parse</code>
   * @return nicely formatted version of this inexact value
   */
  public String toDisplayableString() {
    if (isRange()) {
      return Integer.toString(lowValue) + " - " + Integer.toString(highValue);
    }
    else {
      return Integer.toString(intValue);
    }
  }

  public <V> V accept(ToxmlVisitor<V> visitor) throws Exception {
    return visitor.visit(this);
  }
}

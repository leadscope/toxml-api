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
import com.leadscope.stucco.util.MathUtil;

/**
 * A float value that can optionally be specified as a range
 */
public class InexactValue extends AbstractPrimitiveToxmlObject 
    implements HashableToxmlObject, Displayable, Comparable<InexactValue> {
  
  private float floatValue;
  private float lowValue;
  private float highValue;
  private float comparableValue;
  private boolean isRange;
  
  private static final String NAN_VALUE = "NaN";
  
  /**
   * Convenience factory that parses strings like ">99.9", "< 100" and "5.7E10".
   * Uses positive infinity and negative infinity as range caps.
   * @param string the string to parse
   */
  public static InexactValue parse(String string) {
    return parse(string, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
  }

  /**
   * Convenience factory that parses strings like ">99.9", "< 100" and "5.7E10"
   * @param string the string to parse
   * @param lowestValue the lowest possible value; e.g.. <100 will be a range from
   * this lowestValue to 100; negative infinity ok
   * @param highestValue the highest possible value; e.g. >99 will be a range from
   * 99 to this highestValue; positive infinity ok
   * @return a new inexact value; null if string is null or is empty
   * @exception NumberFormatException if the value could not be parsed
   */
  public static InexactValue parse(String string, float lowestValue, float highestValue) {
    if (string == null) {
      return null;
    }
    
    String trimmed = string.trim();
    if (trimmed.length() == 0) {
      return null;
    }

    // about sign is useless
    if (trimmed.startsWith("~")) {
      trimmed = trimmed.substring(1).trim();
    }

    // maybe dangerous, but we need to remove ,
    trimmed = trimmed.replaceAll("[,]", "");

    if (trimmed.equals(NAN_VALUE)) {
      return null;
    }

    int qualifier = 0;
    if (trimmed.startsWith("<")) {
      qualifier = -1;
      trimmed = trimmed.substring(1).trim();
    }
    else if (trimmed.startsWith(">")) {
      qualifier = 1;
      trimmed = trimmed.substring(1).trim();
    }
    
    int dashIdx = -1;
    int cIdx = 0;
    while (cIdx < trimmed.length()) {
      char c = trimmed.charAt(cIdx);
      if (c == '-') {
        if (!(cIdx > 0 && trimmed.charAt(cIdx-1) == 'E')) {
          dashIdx = cIdx;
          break;
        }
      }
      cIdx++;
    }
    
    if (dashIdx > 0) {
      String low = trimmed.substring(0, dashIdx).trim();
      String high = trimmed.substring(dashIdx + 1, trimmed.length()).trim();
      
      float lowValue;
      float highValue;

      if (low.length() > 0) {
        lowValue = Float.parseFloat(low);

        if (high.length() < 1) {
          highValue = highestValue;
        }
        else {
          highValue = Float.parseFloat(high);
        }
        
        if (qualifier == 0) {
          return new InexactValue(lowValue, highValue);
        }
        else if (qualifier < 0) {
          return new InexactValue(lowestValue, highValue);
        }
        else {
          return new InexactValue(lowValue, highestValue);
        }
      }
    }

    float value = Float.parseFloat(trimmed);
    if (qualifier == 0) {
      return new InexactValue(value);
    }
    else if (qualifier < 0) {
      return new InexactValue(lowestValue, value);
    }
    else {
      return new InexactValue(value, highestValue);
    }   
  }

  public InexactValue() { }
  
  /**
   * uses <code>EQUAL</code> as qualifier
   * @param floatValue the value for this object
   * @exception IllegalArgumentException if floatValue is NaN
   */
  public InexactValue(float floatValue) {
    setValue(floatValue);
  }
  
  /**
   * Defines the inexact value as a range.
   * @param lowValue the low value for this object
   * @param highValue the high value for this object
   * @exception IllegalArgumentException if highValue is less than low value or if
   * either value is NaN
   */
  public InexactValue(float lowValue, float highValue) {
    setValue(lowValue, highValue);
  }
  
  public void setValue(float floatValue) {
    if (Float.isNaN(floatValue)) {
      throw new IllegalArgumentException("Value is NaN; use null object for unknown values");
    }

    this.floatValue = floatValue;
    this.lowValue = floatValue;
    this.highValue = floatValue;
    this.comparableValue = floatValue;
  }
  
  public void setValue(float lowValue, float highValue) {
    if (Float.isNaN(lowValue)) {
      throw new IllegalArgumentException("Low value: is NaN; use infinity for open ended ranges");
    }
    if (Float.isNaN(highValue)) {
      throw new IllegalArgumentException("High value: is NaN; use infinitiy for open ended ranges");
    }
    if (highValue < lowValue) {
      throw new IllegalArgumentException(lowValue + " is greater than " + highValue);
    }

    this.lowValue = lowValue;
    this.highValue = highValue;

    if (lowValue == highValue) {
      floatValue = lowValue;
      comparableValue = lowValue;
      isRange = false;
    }
    else {
      floatValue = Float.NaN;
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
  private float computeComparableValue(float lowValue, float highValue) {
    if (Float.isInfinite(lowValue)){ 
      if (Float.isInfinite(highValue)) {
        comparableValue = 0f;
      }
      else {
        comparableValue = highValue;
      }
    }
    else if (Float.isInfinite(highValue)) {
      comparableValue = lowValue;
    }
    else {
      comparableValue = lowValue + ((highValue - lowValue) / 2f);
    }

    return comparableValue;
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
   * is the actual value.  Otherwise this value will be Float.NaN.
   * @return the central or actual float
   */
  public float getFloatValue() {
    return floatValue;
  }

  /**
   * Gets the lowest possible value for this object.
   * @return the loweset possible value
   */
  public float getLowValue() {
    return lowValue;
  }

  /**
   * Gets the highest possible value for this object
   * @return the highest possible value
   */
  public float getHighValue() {
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
   * Creates a new inexact value which is a multiple of this one
   * @param multiplier the value to multiply this value by
   * @return the new value (including ranges)
   */
  public InexactValue multiply(float multiplier) {
    if (isRange()) {
      return new InexactValue(lowValue * multiplier, highValue * multiplier);
    }
    else {
      return new InexactValue(floatValue * multiplier);
    }
  }

  /**
   * Creates a new inexact value which is a portion of this one
   * @param divisor the value to divide this value by
   * @return the new value (including ranges)
   */
  public InexactValue divide(float divisor) {
    if (isRange()) {
      return new InexactValue(lowValue / divisor, highValue / divisor);
    }
    else {
      return new InexactValue(floatValue / divisor);
    }
  }
  
  /**
   * Rounds the value to a number of significant digits
   * @param sigFigs the number of significant figures
   * @return the new value rounded to the number of digits
   */
  public InexactValue round(int sigFigs) {
    if (isRange()) {
      return new InexactValue(
          (float)MathUtil.logarithmicRound(lowValue, sigFigs),
          (float)MathUtil.logarithmicRound(highValue, sigFigs));
    }
    else {
      return new InexactValue((float)MathUtil.logarithmicRound(floatValue, sigFigs));
    }
  }

  /**
   * Adds another inexact value to this one and returns the result
   * @param v the other value
   * @return the sum of this value and v
   */
  public InexactValue add(InexactValue v) {
    if (isRange()) {
      if (v.isRange()) {
        return new InexactValue(lowValue + v.getLowValue(), highValue + v.getHighValue());
      }
      else {
        return new InexactValue(lowValue + v.getFloatValue(), highValue + v.getFloatValue());
      }
    }
    else {
      if (v.isRange()) {
        return new InexactValue(floatValue + v.getLowValue(), floatValue + v.getHighValue());
      }
      else {
        return new InexactValue(floatValue + v.getFloatValue());
      }
    }
  }

  public int hashCode() {
    final int prime = 31;
    int result = 17;
    result = prime * result + new Float(highValue).hashCode();
    result = prime * result + new Float(floatValue).hashCode();
    result = prime * result + (isRange ? 1231 : 1237);
    result = prime * result + new Float(lowValue).hashCode();
    return result;
  }

  public boolean isZero() {
    return lowValue == 0f && highValue == 0f;
  }
  
  public boolean equals(Object obj) {
    if (obj == null || !obj.getClass().equals(getClass())) {
      return false;
    }
    InexactValue iv = (InexactValue)obj;

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
      if (Float.isNaN(floatValue)) {
        if (!Float.isNaN(iv.floatValue)) {
          return false;
        }
        else {
          return true;
        }
      }
      else if (Float.isNaN(iv.floatValue)) {
        return false;
      }
      else {
        return floatValue == iv.floatValue;
      }
    }
  }

  /**
   * @param other the object to compare with this one
   * @return 1 if this object is greater than o, 0 if they are equal,
   * and -1 if this object is less than o
   * @exception ClassCastException if o is not of type TypedValue
   */
  public int compareTo(InexactValue other) {
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
      if (Float.isInfinite(lowValue)) {
        if (Float.isInfinite(highValue)) {
          return "";
        }
        else {
          return "< " + Float.toString(highValue);
        }
      }
      else if (Float.isInfinite(highValue)) {
        return "> " + Float.toString(lowValue);
      }
      else {
        return Float.toString(lowValue) + " - " + Float.toString(highValue);
      }
    }
    else {
      return Float.toString(floatValue);
    }
  }

  public <V> V accept(ToxmlVisitor<V> visitor) throws Exception {
    return visitor.visit(this);
  }
}

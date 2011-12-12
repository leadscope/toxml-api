/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

import java.util.*;

import com.leadscope.stucco.util.Displayable;

/**
 * An InexactValue in a given Units. This is implemented as a composite for convenient
 * deserializing and serializing. It should generally be used as a primitive value object;
 * i.e. use the full constructor, and never make updates except during deserialization.
 */
public class Quantity extends AbstractCompositeToxmlObject 
     implements Comparable<Quantity>, Displayable, HashableToxmlObject {
  
  private InexactValue value;
  private Units units;
  
  public static final String VALUE_TAG = "Value";
  public static final String UNITS_TAG = "Units";
  
  private static final List<String> childTags = Arrays.asList(VALUE_TAG, UNITS_TAG); 
  
  /**
   * Attempts to parse the numeric value and associate it with the given units.
   * If the value is null, then this returns null'
   * @param valueString the string indiciating the numeric value
   * @param units the units to use 
   * @return the parsed quantity; null if valueString is null or empty
   */
  public static Quantity parse(String valueString, Units units) {
    InexactValue value = InexactValue.parse(valueString);
    if (value == null) {
      return null;
    }
    return new Quantity(value, units);      
  }
  
  /**
   * Attempts to parse the quantity where the value and the units are separated
   * by a space.
   * @param string the string to parse
   * @return the quantity, null if the string is null or the empty string
   * @exception RuntimeException if there is a problem parsing
   */
  public static Quantity parse(String string) {
    if (string == null) {
      return null;
    }
    string = string.trim();
    if (string.length() == 0) {
      return null;
    }
    
    StringTokenizer st = new StringTokenizer(string, " ");
    if (st.countTokens() != 2) {
      throw new RuntimeException("Wrong number of words for [" + string + "]");
    }
    
    String valueString = st.nextToken();
    String unitsString = st.nextToken();
    
    return new Quantity(InexactValue.parse(valueString), new Units(unitsString));        
  }

  /**
   * This method should only be used during deserialization
   */
  public Quantity() { }
  
  /**
   * The preferred way a Quantity should be constructed from an application -
   * in addition to the parse() factory methods
   * @param value the value of the quantity
   * @param units the units that the value is expressed in
   */
  public Quantity(InexactValue value, Units units) {
    setValue(value);
    setUnits(units);
  }
   
  /**
   * @return the numeric value of the quantity
   */
  public InexactValue getValue() {
    return value;
  }
  
  /**
   * @return the units that the value is expressed in
   */
  public Units getUnits() {
    return units;
  }

  /**
   * Attempts to add a quantity to this one; normalizing the units to
   * this quantity's units.
   * @param q the quantity to add to this one
   * @return the sum of this quantity and q
   * @exception IllegalArgumentException if a problem occurs
   */
  public Quantity add(Quantity q) {
    Quantity newQ = q.convertTo(getUnits());
    return new Quantity(newQ.getValue().add(getValue()), getUnits());
  }

  /**
   * Creates a new {@link Quantity} object that uses the new
   * set of units (converting the value).
   * @param units the new units to use
   * @return a new quantity in the given units
   */
  public Quantity convertTo(Units units) {
    InexactValue iv = getValue();
    if (iv.isRange()) {
      Units myUnits = getUnits();
      float lowValue = (float)myUnits.convertTo(iv.getLowValue(), units);
      float highValue = (float)myUnits.convertTo(iv.getHighValue(), units);
      return new Quantity(new InexactValue(lowValue, highValue), units);
    }
    else {
      float newValue = (float)getUnits().convertTo(iv.getFloatValue(), units);
      return new Quantity(new InexactValue(newValue), units);
    }
  }

  /**
   * @param o the object to compare with this one
   * @return true iff o is equivalent to this object
   */
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (!(o.getClass().equals(getClass()))) {
      return false;
    }

    Quantity ov = (Quantity)o;

    return compareTo(ov) == 0;
  }
  
  /**
   * @return the hash for this object
   */
  public int hashCode() {
    Units units = getUnits();
    InexactValue value = getValue();
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((value == null) ? 0 : value.hashCode());
    result = prime * result
        + ((units == null) ? 0 : units.hashCode());
    return result;
  }

  /**
   * @param q the object to compare with this one
   * @return 1 if this object is greater than o, 0 if they are equal,
   * and -1 if this object is less than o
   * @exception ClassCastException if o is not of type TypedValue
   */
  public int compareTo(Quantity q) {
    if (getUnits() != null && q.getUnits() != null) {
      if (!getUnits().equals(q.getUnits())) {
        try {
          q = q.convertTo(getUnits());
        }
        catch (IllegalArgumentException iae) {
          return getUnits().toString().compareTo(q.getUnits().toString());
        }
      }
    }

    InexactValue v = getValue();
    InexactValue qv = q.getValue();
    if (v == null) {
      if (qv == null) {
        return 0;
      }
      else {
        return -1;
      }
    }
    else {
      if (qv == null) {
        return 1;
      }
      else 
        return getValue().compareTo(q.getValue());
    }
  }

  /**
   * Determines if this quantity is between the two given quantities
   * @param low the low end - inclusive
   * @param high the high end - exclusive
   * @exception IllegalArgumentException if the units do not match
   */
  public boolean isBetween(Quantity low, Quantity high) {
    if (getUnits() != null && low.getUnits() != null) {
      if (!getUnits().equals(low.getUnits())) {
        low = low.convertTo(getUnits());
      }
    }
    if (getUnits() != null && high.getUnits() != null) {
      if (!getUnits().equals(high.getUnits())) {
        high = high.convertTo(getUnits());
      }
    }
    if (compareTo(high) >= 0) {
      return false;
    }
    return compareTo(low) >= 0;
  }
  
  /**
   * A nicely formatted version of this quantity
   */
  public String toString() {
    if (getValue() != null && getUnits() != null) {
      return getValue() + " " + getUnits();
    }

    return "";
  }

  /**
   * A nicely formatted version of this quantity
   */
  public String toDisplayableString() {
    if (getValue() != null && getUnits() != null) {
      if (getUnits().isUnitless()) {
        return getValue().toDisplayableString();
      }
      else {
        return getValue().toDisplayableString() + " " + getUnits().toDisplayableString();
      }
    }

    return "";
  }
  /**
   * This method should only be used during deserialization
   */
  public void setValue(InexactValue value) {
    if (value == null) {
      throw new RuntimeException("Cannot have a null value for a quantity");
    }
    
    if (value.getParent() != null) {
      throw new IllegalArgumentException(
          "value is already assigned to another object");
    }
    value.setParent(this);

    InexactValue oldValue = this.value;
    if (oldValue != null) {
      oldValue.setParent(null);
    }
    this.value = value;
    updatedValue(VALUE_TAG, oldValue, value);    
  }

  /**
   * This method should only be used during deserialization
   */
  public void setUnits(Units units) {
    if (units == null) {
      throw new RuntimeException("Cannot have null units for a quantity");
    }

    if (units.getParent() != null) {
      throw new IllegalArgumentException(
          "units is already assigned to another object");
    }
    units.setParent(this);

    Units oldValue = this.units;
    if (oldValue != null) {
      oldValue.setParent(null);
    }
    this.units = units;
    updatedValue(UNITS_TAG, oldValue, units);    
  }

  public List<String> getChildTags() {
    return childTags;
  }

  public ToxmlObject getChild(String tag) { 
    if (tag.equals(VALUE_TAG)) {
      return value;
    } 
    else if (tag.equals(UNITS_TAG)) {
      return units;
    }
    else {
      throw new IllegalArgumentException("Unknown tag: " + tag);
    }
  }

  public void setChild(String tag, ToxmlObject value) {
    if (tag.equals(VALUE_TAG)) {
      setValue((InexactValue)value);
    } 
    else if (tag.equals(UNITS_TAG)) {
      setUnits((Units)value);
    }
    else {
      throw new IllegalArgumentException("Unknown tag: " + tag);
    }
  }

  public Class<? extends ToxmlObject> getChildClass(String tag) {
    if (tag.equals(VALUE_TAG)) {
      return InexactValue.class;
    } 
    else if (tag.equals(UNITS_TAG)) {
      return Units.class;
    }
    else {
      throw new IllegalArgumentException("Unknown tag: " + tag);
    }
  }
}

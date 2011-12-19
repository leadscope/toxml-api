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

import java.text.*;
import java.util.*;

import com.leadscope.stucco.util.Displayable;

/**
 * A simple date value object.  Use when only needing to serialize a single
 * date value.
 */
public class DateValue extends AbstractPrimitiveToxmlObject 
    implements Displayable, HashableToxmlObject, Comparable<DateValue>  {
  
  /** indicates a precision of year only */
  public static final int YEAR = 0;
  /** indicates a precision of year and month only */
  public static final int MONTH = 1;
  /** indicates a precision of year, month and day */
  public static final int DAY = 2;
  
  /** value in string format - e.g. 20101128 */
  private String value;
  
  private Date date;
  private int precision;
  private static final Date YEAR_1000;
  static {
    GregorianCalendar cal = new GregorianCalendar();
    cal.set(1000, 0, 0);
    YEAR_1000 = cal.getTime();
  }
  
  private static DateFormat yearFormat = new SimpleDateFormat("yyyy");
  private static DateFormat monthFormat = new SimpleDateFormat("MM/yyyy");
  private static DateFormat dayFormat = new SimpleDateFormat("MM/dd/yyyy");
  
  // note: a little reluctant to add year format to the standard parsing
  // because any integer with fewer than 5 digits will parse as a date.
  
  private static DateFormat[] dayFormats = new DateFormat[] {
    new SimpleDateFormat("MM/dd/yy"),
    dayFormat,
    new SimpleDateFormat("MM-dd-yy"),
    new SimpleDateFormat("MM-dd-yyyy"),    
  };
  
  private static DateFormat[] monthFormats = new DateFormat[] {
    new SimpleDateFormat("MM/yy"),
    monthFormat,
    new SimpleDateFormat("MM-yy"),
    new SimpleDateFormat("MM-yyyy"),
  };
    
  /**
   * Parses the date given year month and date split into strings.
   * @param year the year as a 4 character string; cannot be null
   * @param month the month as a numeric string from 1-12; 0 or null
   * indicates that the month is not used (only the year will be utilized)
   * @param day the day of the month as a numeric string from 1+; 0 or 
   * null indicates that the day is not used
   * @exception IllegalArgumentException if there is a problem parsing
   */
  public static DateValue parse(String year, String month, String day) {
    // we contort to use java's default date parser to handle conventions
    // for converting shorthand years.
    try {
      if (month == null || Integer.parseInt(month) == 0) {
        testYear(year);
        return parse(year, YEAR);
      }
      else if (day == null || Integer.parseInt(day) == 0) {
        testYear(year);
        testMonth(month);
        return parse(month + "/" + year, MONTH);
      }
      else {
        testDay(day);
        testMonth(month);
        testYear(year);
        return parse(month + "/" + day + "/" + year);
      }
    }
    catch (Exception e) {
      throw new IllegalArgumentException("Illegal date format: " + 
          year + " " + month + " " + day, e);
    }
  }
  
  private static void testYear(String year) {
    int i = Integer.parseInt(year);
    if (i < 0 || (i > 99 && i < 1500) || i >= 10000) {
      throw new IllegalArgumentException("Year value " + year + 
          " is not valid (checked between 100 and 1500 or >= 10000)");
    }
  }

  private static void testMonth(String month) {
    int i = Integer.parseInt(month);
    if (i < 1 || i > 12) {
      throw new IllegalArgumentException("Invalid month: " + month);
    }    
  }

  private static void testDay(String day) {
    int i = Integer.parseInt(day);
    if (i < 1 || i > 31) {
      throw new IllegalArgumentException("Invalid day: " + day);
    }    
  }

  /**
   * Tries to parse the given string in a few standard formats
   * @param dateString a string of form MM/dd/yy
   * @return a date value; null if dateString is null or empty
   * @exception IllegalArgumentException if not one of the used formats
   */
  public static DateValue parse(String dateString) {
    if (dateString == null || dateString.trim().length() == 0) {
      return null;
    }

    for (int i = 0; i < dayFormats.length; i++) {
      try {
        Date date = ((DateFormat)dayFormats[i].clone()).parse(dateString);
        return new DateValue(date, DAY);
      }
      catch (ParseException pe) {
        // expecting this
      }
    }

    for (int i = 0; i < monthFormats.length; i++) {
      try {
        Date date = ((DateFormat)monthFormats[i].clone()).parse(dateString);
        return new DateValue(date, MONTH);
      }
      catch (ParseException pe) {
        // expecting this
      }
    }

    throw new IllegalArgumentException("Unknown date format: " + dateString);
  }

  /**
   * Tries to parse the given string in a few standard formats given
   * the level of precision.
   * @param dateString a string of form MM/dd/yy
   * @param precision the precision which to expect, YEAR, MONTH or DAY
   * @return a date value; null if dateString is null or empty
   * @exception IllegalArgumentException if not one of the used formats
   */
  public static DateValue parse(String dateString, int precision) {
    if (dateString == null || dateString.trim().length() == 0) {
      return null;
    }

    if (precision == DAY) {
      for (int i = 0; i < dayFormats.length; i++) {
        try {
          Date date = ((DateFormat)dayFormats[i].clone()).parse(dateString);
          return new DateValue(date, DAY);
        }
        catch (ParseException pe) {
          // expecting this
        }
      }
    }
    else if (precision == MONTH) {
      for (int i = 0; i < monthFormats.length; i++) {
        try {
          Date date = ((DateFormat)monthFormats[i].clone()).parse(dateString);
          return new DateValue(date, MONTH);
        }
        catch (ParseException pe) {
          // expecting this
        }
      }
    }
    else if (precision == YEAR) {
      try {
        Date date = ((DateFormat)yearFormat.clone()).parse(dateString);
        if (YEAR_1000.after(date)) {
          throw new IllegalArgumentException("Year cannot be less than 1000");
        }
        return new DateValue(date, YEAR);
      }
      catch (ParseException pe) {
        // expecting this
      } 
    }

    throw new IllegalArgumentException("Unknown date format: " + dateString);
  }

  public DateValue() { }
  
  /**
   * Defaults to full day precision
   * @param date the date
   */
  public DateValue(Date date) {
    this.date = date;
    this.precision = DAY;
    this.value = serializeDate(date, precision);
  }

  /**
   * Defaults to full day precision
   * @param date the date
   * @param precision the precision of the date, YEAR, MONTH or DAY
   */
  public DateValue(Date date, int precision) {
    this.date = date;
    this.precision = precision;
    this.value = serializeDate(date, precision);
  }

  /**
   * Constructs the date based on the serialized format
   * @param s the date in the serialized format
   */
  public DateValue(String s) {
    setValue(s);
  }
  
  /**
   * Creates the internal representation of the date string from the given
   * date value and precision.
   * @param date the date
   * @param precision the precision at which the date is accurate (YEAR, MONTH, DAY)
   */
  private static final String serializeDate(Date date, int precision) {
    Calendar c = new GregorianCalendar();
    c.setTime(date);
    
    String yearString = String.valueOf(c.get(Calendar.YEAR));
    String monthString = (precision >= MONTH) ? formatMonth(c.get(Calendar.MONTH)) : "00";
    String dayString = (precision >= DAY) ? formatDay(c.get(Calendar.DAY_OF_MONTH)) : "00";
        
    return yearString + monthString + dayString;
  }
  
  /**
   * Formats the int value uses the syntax of this class; 00 represents an unused
   * value; 01 represents the first
   * @param value
   * @return the string version
   */
  private static String formatDay(int value) {
    if (value < 10) {
      return "0" + String.valueOf(value);
    }
    else {
      return String.valueOf(value);
    }
  }

  /**
   * Formats the int value uses the syntax of this class; 00 represents an unused
   * value; 01 represents the first
   * @param value
   * @return the string version
   */
  private static String formatMonth(int value) {
    value++;
    if (value < 10) {
      return "0" + String.valueOf(value);
    }
    else {
      return String.valueOf(value);
    }
  }

  /**
   * Gets the name of this type (as appears in the schema).  If this object
   * should not be defined as global type, but should appear in-line anonymously,
   * then returning null is ok.
   * @return the unique name of this type of object; null indicates that the
   * object should not be defined as a type, but rather be inlined directly.
   */
  public String getSchemaTypeName() {
    return "DateType";
  }
  
  /**
   * Gets the date
   * @return the date
   */
  public Date getDateValue() {
    return date;
  }

  /**
   * Creates a calendar with the date value
   * @return the calendar
   */
  public Calendar createCalendar() {
    GregorianCalendar c = new GregorianCalendar();
    c.setTime(date);
    return c;
  }
  
  /**
   * Creates a displayable version of the date
   * @return a displayable version
   */
  public String toDisplayableString() {
    if (date == null) {
      return null;
    }

    if (precision >= DAY) {
      return dayFormat.format(date);
    }
    else if (precision >= MONTH) {
      return monthFormat.format(date);
    }
    else {
      return yearFormat.format(date);
    }
  }
  
  /**
   * Validates the value and initializes any other state.  Called just after deserialization.
   * @param lineNumber the current line number
   * @param version the version being used
   */
  public void setValue(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Should not have null value - use null instead of " + getClass().getName());
    }
    else if (value.length() == 8) {
      Calendar c = new GregorianCalendar();
      int yearInt = Integer.parseInt(value.substring(0, 4));
      int monthInt = Integer.parseInt(value.substring(4, 6));
      int dayInt = Integer.parseInt(value.substring(6, 8));
      
      if (monthInt == 0) {
        precision = YEAR;
        c.set(yearInt, 0, 1);
      }
      else if (dayInt == 0) {
        precision = MONTH;
        c.set(yearInt, monthInt - 1, 1);
      }
      else {
        precision = DAY;
        c.set(yearInt, monthInt - 1, dayInt);        
      }
      
      date = c.getTime();
      this.value = value;
    }
    else {
      throw new IllegalArgumentException(
          "Unexpected number format (wrong number of digits): " + value);
    }
  }
  
  public DateValue createTestObject(Random random, boolean fillAllValues) throws Exception {
    int type = random.nextInt(4);
    switch (type) {
    case 0: return DateValue.parse("1988", null, null);
    case 1: return DateValue.parse("1988", "11", null);
    default: return DateValue.parse("1988", "11", "2");
    }
  }

  public int compareTo(DateValue other) {
    return value.compareTo(other.value);
  }
  
  public String getValue() {
    return value;
  }
  
  public String toString() {
    return value;
  }
  
  public <V> V accept(ToxmlVisitor<V> visitor) throws Exception {
    return visitor.visit(this);
  }
  
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (!getClass().equals(other.getClass())) {
      return false;
    }
    return date.equals(((DateValue)other).date);
  }
  
  public int hashCode() {
    return date.hashCode();
  }
}

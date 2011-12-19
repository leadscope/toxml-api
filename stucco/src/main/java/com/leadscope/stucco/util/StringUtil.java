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
package com.leadscope.stucco.util;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Some utility functions for constructing and de-constructing strings
 */
public class StringUtil {
  /** the default number of significant figures for formatting data */
  public static final int MAX_SIGNIFICANT_FIGURES = 6;

  /** allows unrounded formatting of double values */
  public static final int UNLIMITED_SIGNIFICANT_FIGURES = Integer.MAX_VALUE;

  private static final String NO_VALUE_STRING = "";

  /**
   * Joins the string versions of the values (toDisplayableString if possible)
   * with the given delimiter
   * @param values the list of values to join - null values will be presented as null
   * @param delimiter the delimiter to use - e.g. ", "
   * @return the joined string
   */
  public static String join(List<? extends Object> values, String delimiter) {
    StringBuilder sb = new StringBuilder();
    for (Object value : values) {
      if (sb.length() > 0) {
        sb.append(delimiter);
      }
      if (value == null) {
        sb.append("null");
      }
      else if (value instanceof Displayable) {
        sb.append(((Displayable) value).toDisplayableString());
      }
      else {
        sb.append(value.toString());
      }
    }
    return sb.toString();
  }  
  
  /**
   * Formats the property value in an eye friendly way; uses 3 significant figures
   */
  public static String formatEyeFriendlyProperty(double propValue) {
    String formatterString;
    if (propValue == 0) {
      return "0";
    }

    double absValue = (propValue < 0) ? propValue * -1 : propValue;

    if ((absValue < 0.01) || (absValue >= 10000)) {
      formatterString = ".###E0";
    } else if (absValue < 0.1) {
      formatterString = ".0000";
    } else if (absValue < 1) {
      formatterString = ".000";
    } else if (absValue < 10) {
      formatterString = "0.00";
    } else if (absValue < 100) {
      formatterString = "00.0";
    } else {
      formatterString = "#000";
    }

    DecimalFormat formatter = new DecimalFormat(formatterString);
    return formatter.format(propValue);
  }

  public static String formatProperty(Number number) {
    return formatProperty(number, MAX_SIGNIFICANT_FIGURES);
  }
  
  public static String formatProperty(Number number, int sigfigs) {
    if(number == null) return NO_VALUE_STRING;
    return formatProperty(number.doubleValue(), sigfigs);
  }
  
  /**
   * Return a formatted string value for the property using the new logrithmic-based
   * rounding.  Uses the default number of significant digits.
   * @param propValue the value to format
   * @return a formatted string
   */
  public static String formatProperty(double propValue){
    return formatProperty(propValue, MAX_SIGNIFICANT_FIGURES);
  }
  
  /**
   * Return a formatted string value for the property using the new logrithmic-based
   * rounding.
   * @param propValue the value to format
   * @param sigfigs the number of significant digits;  MAX_SIGNIFICANT_FIGURES for the
   * default presentation, or UNLIMITED_SIGNIFICANT_FIGURES to accurately represent
   * the double value.
   * @return a formatted string
   */
  public static String formatProperty(double propValue, int sigfigs){

    if(propValue == Double.NEGATIVE_INFINITY) return "<<";

    if(propValue == Double.POSITIVE_INFINITY) return ">>";

    if(Double.isNaN(propValue)) return "NaN";

    if (sigfigs != UNLIMITED_SIGNIFICANT_FIGURES) {
        double roundedValue = MathUtil.logarithmicRound(propValue,sigfigs);
        return (""+ roundedValue);
    } else {
        return ("" + propValue);
    }
  }

  // Return a formatted string value for the property
  public static String formatProperty(Double propValue){
    if(propValue == null) return NO_VALUE_STRING;
    return formatProperty(propValue.doubleValue());
  }

  // Return a formatted string value for the property
  public static String formatProperty(Double propValue, int sigfigs){
    if(propValue == null) return NO_VALUE_STRING;
    return formatProperty(propValue.doubleValue(), sigfigs);
  }

  /**
   * @param value
   * @param sigfigs
   * @return a double trimmed to sigfigs specified
   */
  public static double formatPropertyValue(double value, int sigfigs){
    if (Double.isNaN(value) || Double.isInfinite(value)) return value;
    return (new Double(formatProperty(new Double(value), sigfigs)).doubleValue());
  }
}

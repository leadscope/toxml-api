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

import java.math.BigDecimal;

/**
 * Some utility methods to round and commute in toxml's legacy way
 */
public class MathUtil {
  /** for converting natural log to log base 10 */
  final static double NATURAL_LOG_OF_10 = (double)Math.log(10.0);

  
  /**
   * Logarithm base 10.
   *
   * @param f value to compute logarithm of
   * @return logarithm base 10
   */
  public static double log10(double f) {
    return Math.log(f) / NATURAL_LOG_OF_10;
  }

  /**
   * Rounds a value from a logarithmic scale to a specified significant number
   * of digits.  Note that value and return are double.  Java double types have
   * round bugs.
   *
   * @param value value to round
   * @param significantDigits number of digits to preserve
   * @return the rounded value
   */
  public static double logarithmicRound(double value, int significantDigits) {
    if (Double.isInfinite(value)) {
      return value;
    }

    if (Double.isNaN(value)) {
      return Double.NaN;
    }

    double log10 = log10(Math.abs(value));
    int digitsFromDecimalPoint = (int)Math.abs(log10);

    int scale = significantDigits - digitsFromDecimalPoint - 1;
    if (value < 1f && value > -1f)  scale = significantDigits + digitsFromDecimalPoint;
    if (scale >= 0) {
      BigDecimal result = new BigDecimal((double)value).setScale(scale, BigDecimal.ROUND_HALF_UP);
      return result.doubleValue();
    }
    else {
      double roundingFactor = (double)Math.pow(10,scale);
      if (roundingFactor == 0.0f) roundingFactor = 1.0f;
      long lvalue = (long)(value * roundingFactor + 0.5f);
      BigDecimal result = new BigDecimal((double)lvalue);
      result = result.divide(new BigDecimal(roundingFactor),BigDecimal.ROUND_HALF_UP);
      return result.doubleValue();
    }
  }
}

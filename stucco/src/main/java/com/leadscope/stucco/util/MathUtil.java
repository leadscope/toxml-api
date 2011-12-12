/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
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

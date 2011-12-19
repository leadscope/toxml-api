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

/**
 * Some utility methods to round and commute in toxml's legacy way
 */
public class MathUtil {
  /**
   * Rounds the value to the given significant figures
   * @param num the value to round
   * @param n the number of significant figures
   * @return the rounded value
   */
  public static double base10SignificantFiguresRound(double num, int n) {
    if (num == 0) {
      return 0;
    }

    final double d = Math.ceil(Math.log10(num < 0 ? -num: num));
    final int power = n - (int) d;

    final double magnitude = Math.pow(10, power);
    final long shifted = Math.round(num*magnitude);
    return shifted/magnitude;
  }
}

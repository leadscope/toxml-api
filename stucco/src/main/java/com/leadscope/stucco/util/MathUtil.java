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

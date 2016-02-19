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
package com.leadscope.stucco;

/**
 * Exception indicating that an attempt was made to convert a value between
 * incompatible units
 */
public class IncompatibleUnitsException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public IncompatibleUnitsException(UnitsTerm oldTerm, UnitsTerm newTerm) {
    super("Tried to convert from " + 
             oldTerm + " (" + oldTerm.getMetric().getName() + ") to " +
             newTerm + " (" + newTerm.getMetric().getName() + ")");
  }
}

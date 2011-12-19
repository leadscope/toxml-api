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

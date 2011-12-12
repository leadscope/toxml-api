/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
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

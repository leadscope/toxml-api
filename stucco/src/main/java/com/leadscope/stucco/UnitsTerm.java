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

import com.leadscope.stucco.util.Displayable;

/**
 * A term of units - might be for a specific metric with other terms that
 * can normalized with. Immutable to support thread-safe KnownMetrics singleton.
 */
public class UnitsTerm implements Displayable {
  private String term;
  private Metric metric;
  private boolean fractionOfBase;
  public long magnitude = 1;
    
  /**
   * Constructs a new term for an unknown metric
   * @param term the term
   */
  public UnitsTerm(String term) {
    if (term == null) {
      throw new IllegalArgumentException("Cannot have null term");
    }
    if (term.contains("/")) {
      throw new IllegalArgumentException("term cannot contain the / units separator");
    }
    this.term = term;    
    this.metric = new Metric(KnownMetrics.UNKNOWN_METRIC);    
  }
  
  /**
   * @param term the term
   * @param metric the metric being represented by this term - null indicates
   * that the term stands alone and cannot be converted to any other terms
   * @param fractionOfBase true iff the magnitude is a fraction of the Metric base - false
   * iff the magnitude is a multiplier of the base
   * @param magnitude magnitude relative to the base of the Metric
   */
  public UnitsTerm(String term, Metric metric, boolean fractionOfBase, long magnitude) {
    if (term == null) {
      throw new IllegalArgumentException("Cannot have null term");
    }
    if (term.contains("/")) {
      throw new IllegalArgumentException("term cannot contain the / units separator");
    }
    if (metric == null) {
      throw new IllegalArgumentException("Cannot have null metric");
    }
    this.term = term;
    this.metric = metric;
    this.fractionOfBase = fractionOfBase;
    this.magnitude = magnitude;
  }
  
  /**
   * @return the term
   */
  public String getTerm() {
    return term;
  }
  
  /**
   * @return the metric being represented by this term - null indicates
   * that the term stands alone and cannot be converted to any other terms
   */
  public Metric getMetric() {
    return metric;
  }

  /**
   * @return true iff the magnitude is a fraction of the Metric base - false
   * iff the magnitude is a multiplier of the base
   */
  public boolean isFractionOfBase() {
    return fractionOfBase;
  }

  /**
   * @return magnitude relative to the base of the Metric
   */
  public long getMagnitude() {
    return magnitude;
  }
  
  public int hashCode() {
    return term.hashCode();
  }

  public String toString() {
    return term;
  }
  
  public String toDisplayableString() {
    return term;
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    UnitsTerm other = (UnitsTerm) obj;
    if (term == null) {
      if (other.term != null)
        return false;
    } else if (!term.equals(other.term))
      return false;
    return true;
  }

  /**
   * Normalizes a value in this term to another term with the same metric
   * @param value the value to normalize
   * @param newTerm the term for the normalized value
   * @IncompatibleUnitsException if the metrics are not compatible
   */
  public double convertTo(double value, UnitsTerm newTerm) throws IncompatibleUnitsException {
    String myMetric = metric.getName();
    String newMetric = newTerm.getMetric().getName();
    if (KnownMetrics.UNKNOWN_METRIC.equals(myMetric) ||
        KnownMetrics.UNKNOWN_METRIC.equals(newMetric)) {
      throw new IncompatibleUnitsException(this, newTerm);
    }
    double baseValue = fractionOfBase ?
        value / magnitude :
        value * magnitude;
        
    return newTerm.fractionOfBase ?
        baseValue * newTerm.magnitude :
        baseValue / newTerm.magnitude;
  }
}

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

import java.util.ArrayList;
import java.util.List;

/**
 * A dimension of data that has a list of UnitsTerm that represent it. The
 * UnitsTerms can be interchanged through normalization based on the base.
 * Immutable once it has been added to the thread-safe KnownMetrics singleton.
 */
public class Metric {
  private String name;
  private List<UnitsTerm> terms = new ArrayList<UnitsTerm>();
  private boolean addedToKnownMetrics;
  
  public Metric(String name) {
    this.name = name;
  }
  
  /**
   * Adds a term to this metric (and sets it as the Metric of the term). This method
   * should not be called again after the metric has been submitted to KnownMetrics.
   * @param term the term
   * @param fractionOfBase true iff the magnitude is a fraction of the Metric base - false
   * iff the magnitude is a multiplier of the base
   * @param magnitude magnitude relative to the base of the Metric
   * @return the created term object that was added
   */
  public UnitsTerm addTerm(String term, boolean fractionOfBase, long magnitude) {
    synchronized(KnownMetrics.getInstance()) {
      if (addedToKnownMetrics) {
        throw new RuntimeException("Metric has already been added to KnownMetrics and cannot be modified");
      }
      for (UnitsTerm oldTerm : terms) {
        if (oldTerm.getTerm().equals(term)) {
          throw new IllegalArgumentException(term + " has already been added as a metric");
        }
      }
      UnitsTerm termObj = new UnitsTerm(term, this, fractionOfBase, magnitude); 
      terms.add(termObj);
      return termObj;
    }
  }
  
  /**
   * Gets the terms in this metric
   * @return the terms
   */
  public List<UnitsTerm> getTerms() {
    return new ArrayList<UnitsTerm>(terms);
  }  
  
  public String getName() {
    return name;
  }
  
  void addedToKnownMetrics() {
    synchronized(KnownMetrics.getInstance()) {
      addedToKnownMetrics = true;
    }
  }
}

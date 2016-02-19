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

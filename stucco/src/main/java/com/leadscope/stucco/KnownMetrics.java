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

import java.util.*;

/**
 * A singleton for making available all known metrics supported by this
 * version of ToxML. - Thread safe.
 */
public class KnownMetrics {
  public static final String UNKNOWN_METRIC = "Unknown Metric";
  public static final String UNITLESS = "Unitless";
  public static final String NONE = "None";

  private static Metric unitlessMetric;
  private static UnitsTerm unitlessTerm;

  private static final KnownMetrics instance;
  static {
    instance = new KnownMetrics();
    unitlessMetric = new Metric(UNITLESS);
    unitlessTerm = unitlessMetric.addTerm(NONE, false, 1);
    instance.addMetric(unitlessMetric);
    try {
      List<Metric> modelMetrics = GeneratedModelAccessor.getKnownMetrics();
      for (Metric metric : modelMetrics) {
        instance.addMetric(metric);
      }
    }
    catch (Throwable t) { 
      t.printStackTrace();
    }
  }
  
  public static final KnownMetrics getInstance() {
    return instance;
  }
        
  private Set<String> metricNames = new HashSet<String>();
  private Map<String, UnitsTerm> terms = new HashMap<String, UnitsTerm>();
  private List<Metric> metrics = new ArrayList<Metric>();
  
  private KnownMetrics() {  }

  public synchronized List<Metric> getMetrics() {
    return new ArrayList<Metric>(metrics);
  }
  
  /**
   * Adds the metric to the list of known metrics
   * @param metric must have a unique name and all units must be unique
   * within it
   */
  public synchronized void addMetric(Metric metric) {
    if (metric.getName() == null) {
      throw new IllegalArgumentException("Metric does not have a name");
    }
    if (metricNames.contains(metric.getName())) {
      throw new IllegalArgumentException("Metric: " + metric.getName() + 
            " has already been added");
    }
    for (UnitsTerm term : metric.getTerms()) {
      UnitsTerm oldTerm = terms.get(term.getTerm()); 
      if (oldTerm != null) {
        throw new IllegalArgumentException("Metric: " + metric.getName() +
            " has a term: " + term.getTerm() + " which has already been defined by: " +
            oldTerm.getMetric().getName());
      }
      terms.put(term.getTerm(), term);
    }
    metrics.add(metric);
    metric.addedToKnownMetrics();
  }
  
  /**
   * Gets the units term for the given string
   * @param term the term to find
   */
  public synchronized UnitsTerm getTerm(String term) {
    return terms.get(term);
  }
  
  /**
   * Gets the unitless metric
   */
  public Metric getUnitlessMetric() {
    return unitlessMetric;
  }
  
  /**
   * Gets the unitless term - None
   */
  public UnitsTerm getUnitlessTerm() {
    return unitlessTerm;
  }
}

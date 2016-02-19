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

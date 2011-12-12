/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

import java.util.List;

/**
 * Provides a way to access a couple of the key components from the generated
 * model without needing to compile against it
 */
public class GeneratedModelAccessor {
  /**
   * Attempts to add the metrics from the generated model.
   */
  @SuppressWarnings("unchecked")
  public static List<Metric> getKnownMetrics() throws Exception {
    Class<?> stuccoMetricsClass = 
        Class.forName("com.leadscope.stucco.model.GeneratedModel");
    return (List<Metric>)
        stuccoMetricsClass.getMethod("getKnownMetrics").invoke(null);
  }
  
  /**
   * @return the class of the root compound object
   */
  @SuppressWarnings("unchecked")
  public static Class<? extends CompositeToxmlObject> getCompoundClass() throws Exception {
    Class<?> stuccoMetricsClass = 
        Class.forName("com.leadscope.stucco.model.GeneratedModel");
    return (Class<? extends CompositeToxmlObject>)
        stuccoMetricsClass.getMethod("addKnownMetrics").invoke(null);
  }
}

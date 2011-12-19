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

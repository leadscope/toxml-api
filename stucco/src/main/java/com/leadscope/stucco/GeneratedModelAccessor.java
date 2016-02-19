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

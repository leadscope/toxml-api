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
package com.leadscope.stucco.model;

import com.leadscope.stucco.Quantity;
import com.leadscope.stucco.Units;

import junit.framework.TestCase;

public class UnitsTestCase extends TestCase {
  /**
   * This test requires the generated model classes to be available
   */
  public void testBasicUnits() throws Throwable {
    Quantity dose = Quantity.parse("100 mg/kg/day");
    Quantity converted = dose.convertTo(new Units("ug/kg/day"));
    assertEquals("Should have more micrograms",
        100f * 1000f, converted.getValue().getFloatValue(), 0.00001f);
    
    converted = dose.convertTo(new Units("mg/kg/hour"));
    assertEquals("Should have fewer per hour",
        100f / 24f, converted.getValue().getFloatValue(), 0.00001f);
    
    converted = dose.convertTo(new Units("ug/kg/hour"));
    assertEquals("Should have right blend of more and fewer",
        100f * 1000f / 24f, converted.getValue().getFloatValue(), 0.00001f);
  }
}

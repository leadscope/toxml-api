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

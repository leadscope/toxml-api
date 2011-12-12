/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
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

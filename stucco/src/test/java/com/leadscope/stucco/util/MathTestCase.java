package com.leadscope.stucco.util;

import junit.framework.TestCase;

public class MathTestCase extends TestCase {
  public void testRound() {
    assertEquals("0.12345", StringUtil.formatProperty(0.12345123, 5));
    assertEquals("123000", StringUtil.formatProperty(123456, 3));
    assertEquals("123456", StringUtil.formatProperty(123456, 6));
    assertEquals("123456.0", StringUtil.formatProperty(123456, 7));
    assertEquals("123460", StringUtil.formatProperty(123456, 5));
    // we currently aren't adding 0's to the right of the decimal
    assertEquals("123456.0", StringUtil.formatProperty(123456, 8));
    assertEquals("1.2E-8", StringUtil.formatProperty(0.000000012341234, 2));
    assertEquals("1.23E-8", StringUtil.formatProperty(0.000000012300000, 3));
    // we currently aren't adding 0's to the right of the decimal
    assertEquals("1.23E-8", StringUtil.formatProperty(0.000000012300000, 4));
  }
}

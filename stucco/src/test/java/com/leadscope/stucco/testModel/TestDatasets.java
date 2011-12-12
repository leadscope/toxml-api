/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.testModel;

import com.leadscope.stucco.AbstractToxmlObjectList;

public class TestDatasets extends AbstractToxmlObjectList<TestDatum> {
  public String getChildTag() {
    return "Datum";
  }

  public Class<TestDatum> getChildClass() {
    return TestDatum.class;
  }
}

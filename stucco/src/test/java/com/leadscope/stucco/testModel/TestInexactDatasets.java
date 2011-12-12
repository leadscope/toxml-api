/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.testModel;

import com.leadscope.stucco.AbstractToxmlObjectList;

public class TestInexactDatasets extends AbstractToxmlObjectList<TestInexactDatum> {
  public String getChildTag() {
    return "Datum";
  }

  public Class<TestInexactDatum> getChildClass() {
    return TestInexactDatum.class;
  }
}

/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.testModel;

import java.util.Arrays;
import java.util.List;

import com.leadscope.stucco.*;

public class TestCompound extends AbstractCompositeToxmlObject {
  private TestDatasets datasets;
  private TestInexactDatasets inexactDatasets;
  private TestStructure structure;
  
  public TestDatasets getDatasets() {
    if (datasets == null) {
      setDatasets(new TestDatasets());
    }
    return datasets;
  }

  public TestInexactDatasets getInexactDatasets() {
    if (inexactDatasets == null) {
      setInexactDatasets(new TestInexactDatasets());
    }
    return inexactDatasets;
  }

  public TestStructure getStructure() {
    return structure;
  }
  
  public void setDatasets(TestDatasets datasets) {
    this.datasets = datasets;
    datasets.setParent(this);
  }

  public void setInexactDatasets(TestInexactDatasets datasets) {
    this.inexactDatasets = datasets;
    datasets.setParent(this);
  }

  public void setStructure(TestStructure structure) {
    this.structure = structure;
    structure.setParent(this);
  }
  
  public List<String> getChildTags() {
    return Arrays.asList("Datasets", "Structure");
  }

  public ToxmlObject getChild(String tag) {
    if ("Datasets".equals(tag)) {
      return getDatasets();
    }
    else if ("InexactDatasets".equals(tag)) {
      return getInexactDatasets();
    }
    else if ("Structure".equals(tag)) {
      return getStructure();
    }    
    throw new IllegalArgumentException("Unknown tag: " + tag);
  }

  public void setChild(String tag, ToxmlObject value) {
    if ("Datasets".equals(tag)) {
      setDatasets((TestDatasets)value);
    }
    else if ("InexactDatasets".equals(tag)) {
      setInexactDatasets((TestInexactDatasets)value);
    }
    else if ("Structure".equals(tag)) {
      setStructure((TestStructure)value);
    }    
    else {
      throw new IllegalArgumentException("Unknown tag: " + tag);
    }
  }

  public Class<? extends ToxmlObject> getChildClass(String tag) {
    if ("Datasets".equals(tag)) {
      return TestDatasets.class;
    }
    else if ("InexactDatasets".equals(tag)) {
      return TestInexactDatasets.class;
    }
    else if ("Structure".equals(tag)) {
      return TestStructure.class;
    }    
    throw new IllegalArgumentException("Unknown tag: " + tag);    
  }
}

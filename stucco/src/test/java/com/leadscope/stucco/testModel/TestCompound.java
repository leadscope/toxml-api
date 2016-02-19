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

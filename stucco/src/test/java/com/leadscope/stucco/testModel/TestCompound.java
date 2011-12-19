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

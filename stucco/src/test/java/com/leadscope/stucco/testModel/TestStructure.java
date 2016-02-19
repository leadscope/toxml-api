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

public class TestStructure extends AbstractCompositeToxmlObject {
  private StringValue molfile;
  private StringValue smiles;
  
  public StringValue getMolfile() {
    return molfile;
  }

  public void setMolfile(StringValue molfile) {
    this.molfile = molfile;
    molfile.setParent(this);
  }

  public StringValue getSmiles() {
    return smiles;
  }
  
  public void setSmiles(StringValue smiles) {
    this.smiles = smiles;
    smiles.setParent(this);
  }
  
  public List<String> getChildTags() {
    return Arrays.asList("Molfile", "Smiles");
  }

  public ToxmlObject getChild(String tag) {
    if ("Molfile".equals(tag)) {
      return getMolfile();
    }
    else if ("Smiles".equals(tag)) {
      return getSmiles();
    }    
    throw new IllegalArgumentException("Unknown tag: " + tag);
  }

  public void setChild(String tag, ToxmlObject value) {
    if ("Molfile".equals(tag)) {
      setMolfile((StringValue)value);
    }
    else if ("Smiles".equals(tag)) {
      setSmiles((StringValue)value);
    }    
    else {
      throw new IllegalArgumentException("Unknown tag: " + tag);
    }
  }

  public Class<? extends ToxmlObject> getChildClass(String tag) {
    if ("Molfile".equals(tag)) {
      return StringValue.class;
    }
    else if ("Smiles".equals(tag)) {
      return StringValue.class;
    }    
    throw new IllegalArgumentException("Unknown tag: " + tag);    
  }
}

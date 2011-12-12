/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
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

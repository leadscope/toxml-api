/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.testModel;

import java.util.Arrays;
import java.util.List;

import com.leadscope.stucco.*;

public class TestInexactDatum extends AbstractCompositeToxmlObject {
  private StringValue name;
  private InexactValue value;
  
  public StringValue getName() {
    return name;
  }

  public void setName(StringValue name) {
    this.name = name;
    name.setParent(this);
  }

  public InexactValue getValue() {
    return value;
  }
  
  public void setValue(InexactValue value) {
    this.value = value;
    value.setParent(this);
  }
  
  public List<String> getChildTags() {
    return Arrays.asList("Name", "Value");
  }

  public ToxmlObject getChild(String tag) {
    if ("Name".equals(tag)) {
      return getName();
    }
    else if ("Value".equals(tag)) {
      return getValue();
    }    
    throw new IllegalArgumentException("Unknown tag: " + tag);
  }

  public void setChild(String tag, ToxmlObject value) {
    if ("Name".equals(tag)) {
      setName((StringValue)value);
    }
    else if ("Value".equals(tag)) {
      setValue((InexactValue)value);
    }   
    else {
      throw new IllegalArgumentException("Unknown tag: " + tag);
    }
  }

  public Class<? extends ToxmlObject> getChildClass(String tag) {
    if ("Name".equals(tag)) {
      return StringValue.class;
    }
    else if ("Value".equals(tag)) {
      return InexactValue.class;
    }    
    throw new IllegalArgumentException("Unknown tag: " + tag);    
  }
}

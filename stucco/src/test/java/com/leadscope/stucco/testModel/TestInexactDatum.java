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

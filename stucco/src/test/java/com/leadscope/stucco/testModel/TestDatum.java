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

public class TestDatum extends AbstractCompositeToxmlObject {
  private StringValue name;
  private StringValue value;
  
  public StringValue getName() {
    return name;
  }

  public void setName(StringValue name) {
    this.name = name;
    name.setParent(this);
  }

  public StringValue getValue() {
    return value;
  }
  
  public void setValue(StringValue value) {
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
      setValue((StringValue)value);
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
      return StringValue.class;
    }    
    throw new IllegalArgumentException("Unknown tag: " + tag);    
  }
}

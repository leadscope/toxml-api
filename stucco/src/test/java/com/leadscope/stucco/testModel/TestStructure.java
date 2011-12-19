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

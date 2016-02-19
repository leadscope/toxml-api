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
package com.leadscope.stucco.model;

import junit.framework.TestCase;

import com.leadscope.stucco.DiffStatus;
import com.leadscope.stucco.TypedValue;
import com.leadscope.stucco.io.ToxmlParser;
import com.leadscope.stucco.io.ToxmlWriter;
import com.leadscope.stucco.model.CompoundRecord;
import com.leadscope.stucco.model.Datum;

public class CompoundTestCase extends TestCase {
  public void testCompound() throws Throwable {
    CompoundRecord cr = new CompoundRecord();
    cr.getIds().addChild(new TypedValue("cas", "123-45-5"));
    Datum datum = cr.getDatasets().addNew();
    datum.setNameValue("Foo");
    datum.setValueValue(32.1f);
    
    // abstract attributes
    datum.addSource("foo");
    datum.setDiffStatus(DiffStatus.CONFLICT);
    datum.setOriginalConflictValue("bar");
    
    
    String xml = ToxmlWriter.toString("Compound", cr);
    System.out.println(xml);
    
    CompoundRecord cr2 = ToxmlParser.parse(xml, CompoundRecord.class);
    
    assertEquals("Should be able to round trip data name", 
        cr.getDatasets().get(0).getName(),
        cr2.getDatasets().get(0).getName());
    assertEquals("Should be able to round trip data value", 
        cr.getDatasets().get(0).getValue(),
        cr2.getDatasets().get(0).getValue());
    assertEquals("Should be able to round trip source", 
        "foo",
        cr2.getDatasets().get(0).getSources().get(0));
    assertEquals("Should be able to round trip diff status", 
        DiffStatus.CONFLICT,
        cr2.getDatasets().get(0).getDiffStatus());
    assertEquals("Should be able to round trip conflict value", 
        "bar",
        cr2.getDatasets().get(0).getOriginalConflictValue());
    
  }
}

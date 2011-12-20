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
package com.leadscope.stucco;

import junit.framework.TestCase;

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

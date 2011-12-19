package com.leadscope.stucco;

import junit.framework.TestCase;

import com.leadscope.stucco.io.ToxmlReader;
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
    
    CompoundRecord cr2 = ToxmlReader.parse(xml, CompoundRecord.class);
    
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

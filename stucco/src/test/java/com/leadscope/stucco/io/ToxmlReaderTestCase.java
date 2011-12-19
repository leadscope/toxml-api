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
package com.leadscope.stucco.io;

import junit.framework.TestCase;

import com.leadscope.stucco.InexactValue;
import com.leadscope.stucco.testModel.TestCompound;

public class ToxmlReaderTestCase extends TestCase {
  public void testSimple() throws Throwable {
    TestCompound compound = ToxmlReader.parse("<Compound>" +
       "<Datasets>" +
         "<Datum><Name>Foo</Name><Value>Bar</Value></Datum>" +
         "<Datum><Name>Boo</Name><Value>Urns</Value></Datum>" +
       "</Datasets>" +
       "<Structure><Smiles>OOOO</Smiles></Structure>" +
       "</Compound>", TestCompound.class);
    
    assertEquals(compound.getDatasets().get(1).getName().getValue(), "Boo");
    assertEquals(compound.getDatasets().get(1).getValue().getValue(), "Urns");
  }
  
  public void testWhitespace() throws Throwable {
    TestCompound compound = ToxmlReader.parse("  <Compound>  " +
       "<Datasets>    " +
         "<Datum> <Name>Foo</Name>  <Value> Bar</Value></Datum>" +
         "<Datum><Name>Boo</Name><Value>Urns  </Value></Datum>" +
       "</Datasets>  " +
       "<Structure>  <Smiles>OOOO  </Smiles></Structure>" +
       "</Compound>  ", TestCompound.class);
    
    assertEquals("Boo", compound.getDatasets().get(1).getName().getValue());
    assertEquals("Urns", compound.getDatasets().get(1).getValue().getValue());
    assertEquals("OOOO", compound.getStructure().getSmiles().getValue());
  }
  
  public void testBadTags() throws Throwable {
    try {
      ToxmlReader.parse("  <Compound>  " +
          "<Datasets>    " +
          "<Datum> <NameBad>Foo</NameBad>  <Value> Bar</Value></Datum>" +
          "<Datum><Name>Boo</Name><Value>Urns  </Value></Datum>" +
          "</Datasets>  " +
          "<Structure>  <Smiles>OOOO  </Smiles></Structure>" +
          "</Compound>  ", TestCompound.class);
      fail("Should have found invalid tag");
    }
    catch (Exception e) {
      assertEquals("Unknown tag: NameBad at line: 1", e.getMessage());
    }
  }
    
  public void testInexactValue() throws Throwable {
    InexactValue value = ToxmlReader.parse("<MyValue>1.3</MyValue>", InexactValue.class);    
    assertEquals(1.3, value.getFloatValue(), 0.001);

    value = ToxmlReader.parse("<MyValue>  " +
        "<LowValue>1.3</LowValue>    " +
        "<HighValue>1.8</HighValue></MyValue>", InexactValue.class);
    assertEquals(1.3, value.getLowValue(), 0.001);
    assertEquals(1.8, value.getHighValue(), 0.001);
    
    value = ToxmlReader.parse("<MyValue qualifier='greater'>1.3</MyValue>", InexactValue.class);
    assertTrue("With qualifier should have range", value.isRange());
    assertEquals("With qualifier greater should have low value",
        1.3, value.getLowValue(), 0.001);
    assertEquals("With qualifier greater should have infinite high value",
        Float.POSITIVE_INFINITY, value.getHighValue());
    
    value = ToxmlReader.parse("<MyValue qualifier='less'>   1.3 </MyValue>", InexactValue.class);
    assertTrue("With qualifier should have range", value.isRange());
    assertEquals("With qualifier less should have high value",
        1.3, value.getHighValue(), 0.001);
    assertEquals("With qualifier greater should have infinite low value",
        Float.NEGATIVE_INFINITY, value.getLowValue());
  }

  public void testMixedInexactValue() throws Throwable {
    TestCompound compound = ToxmlReader.parse("<Compound>" +
        "<Datasets>" +
          "<Datum><Name>Foo</Name><Value>Bar</Value></Datum>" +
          "<Datum><Name>Boo</Name><Value>Urns</Value></Datum>" +
        "</Datasets>" +
        "<InexactDatasets>" +
        "<Datum sources='foo'><Name>Foo</Name><Value><LowValue>1.3</LowValue>    " +
        "<HighValue>1.8</HighValue></Value></Datum> " +
        "<Datum><Name>Foo</Name><Value><LowValue>1.3</LowValue>" +
        "<HighValue>1.8</HighValue></Value></Datum>" +
           "<Datum><Name>Bar</Name><Value qualifier='greater'>1.3    " +
           "</Value></Datum> " +          
        "</InexactDatasets>" +
        "<Structure><Smiles>OOOO</Smiles></Structure>" +
        "</Compound>", TestCompound.class);
     
    assertEquals("foo", compound.getInexactDatasets().get(0).getSources().get(0));
    assertEquals(1.3, compound.getInexactDatasets().get(0).getValue().getLowValue(), 0.001);
    assertEquals(1.8, compound.getInexactDatasets().get(0).getValue().getHighValue(), 0.001); 
  }
  
  public void testQualifiedMixedInexactValue() throws Throwable {
    TestCompound compound = ToxmlReader.parse("<Compound>" +
        "<Datasets>" +
          "<Datum><Name>Foo</Name><Value>Bar</Value></Datum>" +
          "<Datum><Name>Boo</Name><Value>Urns</Value></Datum>" +
        "</Datasets>" +
        "<InexactDatasets>" +
        "<Datum><Name>Bar</Name><Value qualifier='greater'>1.3" +
        "</Value></Datum> " +
        "<Datum><Name>Foo</Name><Value><LowValue>1.3</LowValue>    " +
        "<HighValue>1.8</HighValue></Value></Datum> " +
        " <Datum><Name>Bar</Name><Value qualifier='greater'>1.3    " +
        "</Value></Datum> " +
        "  <Datum><Name>Bar</Name><Value qualifier='greater'>1.3    " +
        "</Value></Datum> " +        
        "</InexactDatasets>" +
        "<Structure><Smiles>OOOO</Smiles></Structure>" +
        "</Compound>", TestCompound.class);
     
    assertEquals(1.3, compound.getInexactDatasets().get(0).getValue().getLowValue(), 0.001);
    assertEquals(Float.POSITIVE_INFINITY, compound.getInexactDatasets().get(0).getValue().getHighValue());
  }
  
  public void testBadTypeExtraText() throws Throwable {
    try {
      ToxmlReader.parse("<Compound>" +
          "<Datasets>    " +
          "<Datum>PlainValue</Datum>" +
          "<Datum><Name>Boo</Name><Value>Urns  </Value></Datum>" +
          "</Datasets>  " +
          "<Structure>  <Smiles>OOOO  </Smiles></Structure>" +
          "</Compound>  ", TestCompound.class);
      fail("Should have found extra text in Datum");
    }
    catch (Exception e) {
      System.out.println("Correctly got exception: " + e.getMessage());
    }
  }
}

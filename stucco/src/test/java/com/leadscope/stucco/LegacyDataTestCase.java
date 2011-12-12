/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

import java.io.File;

import junit.framework.TestCase;

import com.leadscope.stucco.io.*;
import com.leadscope.stucco.model.CompoundRecord;

public class LegacyDataTestCase extends TestCase {  
  public void testReading() throws Throwable {
    File inputFile = new File("src/test/resources/fdagenetox-test.xml"); 
    ToxmlReader.parseList(
        inputFile, 
        CompoundRecord.class,
        new ToxmlHandler<CompoundRecord>() {
          public void handle(CompoundRecord obj) throws Exception {
            System.out.print(obj.getIds().get(0).toDisplayableString() + " ");           
          }
        });
    System.out.println();
  }
  
  public void testReadingAndWriting() throws Throwable {
    File outputDir = new File("target/test-result-files");
    outputDir.mkdirs();
    File outputFile = new File(outputDir, "LegacyDataTestCase-fdagenetox.xml");
    File inputFile = new File("src/test/resources/fdagenetox-test.xml"); 
    
    ToxmlWriter.write("Compounds", "Compound",
        new ToxmlFileSource<CompoundRecord>(inputFile, CompoundRecord.class), 
        outputFile);
  }
}

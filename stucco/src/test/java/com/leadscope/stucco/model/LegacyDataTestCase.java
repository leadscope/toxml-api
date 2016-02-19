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

import java.io.File;

import junit.framework.TestCase;

import com.leadscope.stucco.io.*;

public class LegacyDataTestCase extends TestCase {  
  public void testReading() throws Throwable {
    File inputFile = new File("src/test/resources/legacy-test.xml"); 
    ToxmlParser.parseList(
        inputFile, 
        CompoundRecord.class,
        new ToxmlHandler<CompoundRecord>() {
          public void handle(CompoundRecord obj) throws Exception {
            System.out.print(obj.getIds().get(0).toDisplayableString() + " ");           
          }
        });
    System.out.println();
  }
  
  public void testIterableReading() throws Throwable {
    File inputFile = new File("src/test/resources/legacy-test.xml");
    Iterable<CompoundRecord> source =
      new ToxmlFileSource<CompoundRecord>(inputFile, CompoundRecord.class);
    for (CompoundRecord cr : source) {
      System.out.println(cr.getIds().get(0).getValue());
    }
  }
  
  public void testReadingAndWriting() throws Throwable {
    File outputDir = new File("target/test-result-files");
    outputDir.mkdirs();
    File outputFile = new File(outputDir, "LegacyDataTestCase-output.xml");
    File inputFile = new File("src/test/resources/legacy-test.xml"); 
    
    ToxmlWriter.write("Compounds", "Compound",
        new ToxmlFileSource<CompoundRecord>(inputFile, CompoundRecord.class), 
        outputFile);
  }
  
  public void testInvalid() throws Throwable {
    File inputFile = new File("src/test/resources/invalid-test.xml");
    try {
      ToxmlParser.parseList(inputFile, CompoundRecord.class,
        new ToxmlHandler<CompoundRecord>() {
          public void handle(CompoundRecord obj) throws Exception {
          }
        });    
      fail("Should have gotten an exception while parsing invalid xml file");
    }
    catch (Exception e) {
      assertTrue("Should have found the not supposed to be here element", 
          e.getMessage().startsWith("Unexpected element: ThisIsNotSupposedToBeHere"));
    }
  }
}

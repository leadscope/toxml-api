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

import java.io.File;

import junit.framework.TestCase;

import com.leadscope.stucco.io.*;
import com.leadscope.stucco.model.CompoundRecord;

public class LegacyDataTestCase extends TestCase {  
  public void testReading() throws Throwable {
    File inputFile = new File("src/test/resources/legacy-test.xml"); 
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
    File outputFile = new File(outputDir, "LegacyDataTestCase-output.xml");
    File inputFile = new File("src/test/resources/legacy-test.xml"); 
    
    ToxmlWriter.write("Compounds", "Compound",
        new ToxmlFileSource<CompoundRecord>(inputFile, CompoundRecord.class), 
        outputFile);
  }
}

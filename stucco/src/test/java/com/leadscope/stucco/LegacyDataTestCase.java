/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

import java.io.File;

import junit.framework.TestCase;

import com.leadscope.stucco.io.ToxmlHandler;
import com.leadscope.stucco.io.ToxmlReader;
import com.leadscope.stucco.model.CompoundRecord;

public class LegacyDataTestCase extends TestCase {  
  public void testFdaGenetox() throws Throwable {
    ToxmlReader.parseList(
        new File("src/test/resources/fdagenetox-test.xml"), 
        CompoundRecord.class,
        new ToxmlHandler<CompoundRecord>() {
          public void handle(CompoundRecord obj) {
            System.out.println(obj.getIds().get(0).getValue());
          }
        });
  }
}

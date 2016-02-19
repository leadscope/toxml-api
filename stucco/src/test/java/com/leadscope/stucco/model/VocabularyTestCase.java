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

import com.leadscope.stucco.model.bacterialMutagenesis.BacterialMutagenesisTestSystem;

public class VocabularyTestCase extends TestCase {
  public void testSimple() throws Throwable {
    BacterialMutagenesisTestSystem ts = new BacterialMutagenesisTestSystem();
    assertTrue("Should have salmonella",
        ts.getSpeciesVocabulary().contains("Salmonella typhimurium"));
    assertFalse("Should not have TA100 when salmonella is not the species",
        ts.getStrainVocabulary().contains("TA100"));
    
    ts.setSpeciesValue("Salmonella typhimurium");
    
    assertTrue("Should have TA100 when salmonella is the species",
        ts.getStrainVocabulary().contains("TA100"));
    
  }
}

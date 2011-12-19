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

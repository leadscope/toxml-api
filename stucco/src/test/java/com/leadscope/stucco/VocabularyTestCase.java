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

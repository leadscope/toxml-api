
package com.leadscope.stucco.builder;

import java.io.*;

import junit.framework.TestCase;

public class TestToxmlModelGenerator extends TestCase {
  public void testGenerator() throws Throwable {
    InputStream specIS = TestToxmlModelGenerator.class.getResourceAsStream("toxml_specification.xml");
    if (specIS == null) {
      throw new RuntimeException("The toxml_specification.xml file could not be found");
    }
    
    ToxmlModelGenerator gen = new ToxmlModelGenerator(specIS);
    ToxmlClass compoundClass = gen.getCompoundClass();
    String result = compoundClass.toString("Compound", 0);
    
    File reportsDir = new File("target/test-result-files");
    reportsDir.mkdirs();
    
    File reportFile = new File(reportsDir, "TestToxmlModelGenerator-summary.txt");
    FileWriter fw = new FileWriter(reportFile);
    fw.write(result);
    fw.close();
    
    File classesFile = new File(reportsDir, "TestToxmlModelGenerator-classes.txt");
    fw = new FileWriter(classesFile);
    for (ToxmlClass toxmlClass : gen.getToxmlClasses()) {
      fw.write(toxmlClass.toString());
      fw.write("\n");
    }
    fw.close();
    
    File pkgDir = new File(reportsDir, "generated-sources");
    for (ToxmlClass toxmlClass : gen.getToxmlClasses()) {
      toxmlClass.generateTemplate(pkgDir);
    }
    gen.getGeneratedModelClass().generateTemplate(pkgDir);
  }
}

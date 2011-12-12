package com.leadscope.stucco.builder;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal which generates the data model source files for the ToxML stucco project
 *
 * @goal generate
 * @phase generate-sources
 * @requiresDependencyResolution compile
 * @author <a href="mailto:smiller@leadscope.com">Scott Miller</a>
 * @version $Id$
 */
public class GenerateToxmlModelPlugin extends AbstractMojo {
  /**
   * Location of the project directory
   * @parameter expression="${project.build.directory}"
   * @required
   */
  private File buildDirectory;

  /**
   * The specification file
   * @parameter
   * @required
   */
  private File specificationFile;
  
  public void execute() throws MojoExecutionException {
    File pkgDir = new File(buildDirectory, "generated-sources/java");
    getLog().info("Generating ToxML model into: " + pkgDir.getAbsolutePath());
    
    if (specificationFile == null) {
      throw new RuntimeException("No specificationFile has been specified");
    }
    if (!specificationFile.exists()) {
      throw new RuntimeException("The specificationFile does not exist: " + 
          specificationFile.getAbsolutePath());
    }
    
    try {      
      if (!pkgDir.exists()) {
        pkgDir.mkdirs();
        ToxmlModelGenerator builder = new ToxmlModelGenerator(specificationFile);
        List<ToxmlClass> toxmlClasses = builder.getToxmlClasses();
        for (ToxmlClass toxmlClass : toxmlClasses) {
          toxmlClass.generateTemplate(pkgDir);
        }
        builder.getGeneratedModelClass().generateTemplate(pkgDir);
      }
    }
    catch (Throwable t) {
      throw new MojoExecutionException("Error generating toxml model source code", t);
    }
  }
}

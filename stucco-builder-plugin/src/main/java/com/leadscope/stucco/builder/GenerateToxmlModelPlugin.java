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

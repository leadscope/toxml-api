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
import java.io.FileWriter;
import java.util.*;

import org.antlr.stringtemplate.*;

public abstract class ToxmlClass {
  private static StringTemplateGroup templateGroup = new StringTemplateGroup("stuccoGroup");
  
  private String name;
  private String studyTypeName;
  private String packageName;
  private boolean hasUnits;
  private String description;
      
  public void setName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Cannot be assigned a null name");
    }
     
    this.name = name;    
  }
  
  public String getName() {
    return name;
  }
  
  public void setHasUnits(boolean hasUnits) {
    this.hasUnits = hasUnits;
    
  }
  public boolean isHasUnits() {
    return hasUnits;
  }

  public String getPackageName() {
    return packageName;
  }
    
  public static String camelCase(String tag) {
    if (tag == null) {
      return tag;
    }
    tag = tag.replace("-", "_");
    if (Character.isUpperCase(tag.charAt(0))) {
      int lowerCaseIdx = 1;
      while (lowerCaseIdx < tag.length() &&
          (Character.isUpperCase(tag.charAt(lowerCaseIdx)) ||
           Character.isDigit(tag.charAt(lowerCaseIdx)))) {
        lowerCaseIdx++;
      }
      if (lowerCaseIdx > 1 && lowerCaseIdx < tag.length()) {
        lowerCaseIdx--;
      }
      
      return tag.substring(0, lowerCaseIdx).toLowerCase() + tag.substring(lowerCaseIdx);
    }
    else {
      return tag;
    }
  }
  
  public void setStudyTypeName(String studyTypeName) {
    this.studyTypeName = studyTypeName;
    this.packageName = camelCase(studyTypeName);
  }  
    
  public String getStudyTypeName() {
    return studyTypeName;
  }
  
  public abstract Collection<String> getChildTypeNames();
  
  public abstract boolean isEquivalent(ToxmlClass other);
  
  public abstract boolean isUsingModelPackage();
  public abstract boolean isUsingUtilPackage();
  
  public String toString(String tag, int indent) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < indent; i++) {
      sb.append("  ");
    }
    sb.append(tag);
    sb.append(" - ");
    sb.append(toString());
    return sb.toString();
  }
  
  public String toString() {
    String result = name + " - " + getClass().getSimpleName();
    if (packageName != null) {
      return packageName + "." + result;
    }
    else {
      return result;
    }
  }
  
  public void generateTemplate(File srcDir) throws Exception {    
    StringTemplate st = templateGroup.getInstanceOf(getTemplateName());
    
    st.setAttribute("class", this);
    st.setAttribute("packageName", packageName);
    st.setAttribute("className", name);
    st.setAttribute("commentString", getCommentString());
    st.setAttribute("usingModelPackage", isUsingModelPackage());
    st.setAttribute("usingUtilPackage", isUsingUtilPackage());
    
    fillInTemplate(st);
    final List<Throwable> errors = new ArrayList<Throwable>();
    st.setErrorListener(new StringTemplateErrorListener() {
      public void error(String msg, Throwable error) {
        errors.add(error);
      }
      public void warning(String msg) { }      
    });
    
    String results = st.toString();
    if (errors.size() > 0) {
      throw new RuntimeException(errors.get(0));
    }
    
    String pkgPath = "com/leadscope/stucco/model";
    if (packageName != null) {
      pkgPath += "/" + packageName;
    }
    File pkgDir = new File(srcDir, pkgPath);
    pkgDir.mkdirs();
    File classFile = new File(pkgDir, name + ".java");    
    FileWriter fw = new FileWriter(classFile);
    try {
      fw.write(results);
    }
    finally {
      try { fw.close(); } catch (Exception e) { }
    }
  }
     
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCommentString() {
    if (description == null) {
      return null;
    }
    else {      
      String escapedString = description.replaceAll("\\*\\/", "");       
      String nextEscape = escapedString.replaceAll("\\*\\/", "");
      while (!nextEscape.equals(escapedString)) {
        escapedString = nextEscape;
        nextEscape = escapedString.replaceAll("\\*\\/", "");
      }
      
      StringBuilder sb = new StringBuilder();
      for (String line : escapedString.split("\n")) {
        line = line.trim();
        if (line.length() > 0) {
          if (sb.length() > 0) {
            sb.append(" * ");
          }
          sb.append(line);
          sb.append("\n");
        }
      }
      return escapedString;      
    }
  }

  protected String getTemplateName() {
    return "com/leadscope/stucco/builder/" + getClass().getSimpleName();
  }

  protected abstract void fillInTemplate(StringTemplate st);
}

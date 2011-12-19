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

import java.util.*;

import org.antlr.stringtemplate.StringTemplate;

public class GeneratedModelClass extends ToxmlClass {
  private List<Metric> metrics = new ArrayList<Metric>();
  private ToxmlClass compoundClass;
  
  public GeneratedModelClass() {
    setName("GeneratedModel");
    setDescription("Bridge class between generated model and main stucco classes");
  }
  
  public void addMetric(Metric metric) {
    metrics.add(metric);
  }
  
  public void setCompoundClass(ToxmlClass compoundClass) {
    this.compoundClass = compoundClass;
  }
  
  public Collection<String> getChildTypeNames() {
    return new ArrayList<String>(0);
  }

  public boolean isEquivalent(ToxmlClass other) {
    return false;
  }

  public ToxmlClass copy(String newName) {
    return new GeneratedModelClass();
  }

  public boolean isUsingModelPackage() {
    return false;
  }

  public boolean isUsingUtilPackage() {
    return true;
  }

  protected void fillInTemplate(StringTemplate st) {
    st.setAttribute("metrics", metrics);
    st.setAttribute("compoundClass", compoundClass.getName());
  }
}

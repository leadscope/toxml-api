/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
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

  public boolean equivalent(ToxmlClass other) {
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

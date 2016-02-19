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

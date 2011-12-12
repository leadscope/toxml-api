/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.builder;

import java.util.ArrayList;
import java.util.List;

public class Metric {
  private String name;
  private boolean caseSensitive;
  private List<Units> units = new ArrayList<Units>();
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public List<Units> getUnits() {
    return units;
  }
  public void addUnits(Units units) {
    this.units.add(units);
  }
  public boolean isCaseSensitive() {
    return caseSensitive;
  }
  public void setCaseSensitive(boolean caseSensitive) {
    this.caseSensitive = caseSensitive;
  }   
}

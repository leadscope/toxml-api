/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.builder;

public class Units {
  private String name;
  private boolean fractionOfBase;
  private long magnitude;
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public boolean isFractionOfBase() {
    return fractionOfBase;
  }
  public void setFractionOfBase(boolean fractionOfBase) {
    this.fractionOfBase = fractionOfBase;
  }
  public long getMagnitude() {
    return magnitude;
  }
  public void setMagnitude(long magnitude) {
    this.magnitude = magnitude;
  }  
}

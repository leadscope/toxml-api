/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.builder;

/**
 * Something that can reference a ToxmlClass type
 */
public interface ToxmlClassParent {
  public ToxmlClass getChildClass();
  
  public void setChildClass(ToxmlClass childClass);
}

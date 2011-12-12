/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

/**
 * Abstract implementation of an object list
 */
public abstract class AbstractToxmlObjectList<T extends ToxmlObject> 
    extends AbstractToxmlObjectContainer<T> implements ToxmlObjectList<T> {
  
  public void addChild(T child) {
    if (child.getParent() != null) {
      throw new IllegalArgumentException("child has already been added to another parent");
    }
    values.add(child);
    child.setParent(this);
    childAdded(child);
  }
  
  public <V> V accept(ToxmlVisitor<V> visitor) throws Exception {
    return visitor.visit(this);
  }
}

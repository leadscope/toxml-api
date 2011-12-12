/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;


/**
 * Abstract implementation of an object list
 */
public abstract class AbstractToxmlObjectSet<T extends HashableToxmlObject> 
     extends AbstractToxmlObjectContainer<T> implements ToxmlObjectSet<T> {
  
  /**
   * Only adds the child if another child is not present. However, a listener event
   * will be fired in any case.
   */
  public void addChild(T child) {
    if (child.getParent() != null) {
      throw new IllegalArgumentException("child has already been added to another parent");
    }
    
    T alreadyPresentChild = null;
    for (T value : values) {
      if (value.equals(child)) {
        for (String source : child.getSources()) {
          value.addSource(source);
        }
        alreadyPresentChild = value;
        break;
      }
    }
    
    if (alreadyPresentChild == null) {
      values.add(child);
      child.setParent(this);
      childAdded(child);
    }
    else {
      childAdded(alreadyPresentChild);
    }    
  }

  public <V> V accept(ToxmlVisitor<V> visitor) throws Exception {
    return visitor.visit(this);
  }
}

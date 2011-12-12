/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

/**
 * A listener to a ToxmlObjectContainer - will be notified when an object is added or
 * removed
 */
public interface ContainerListener<T extends ToxmlObject> {
  /**
   * Called after an object is removed from the container
   * @param container the container that was modified
   * @param value the value that was removed
   */
  public void removed(ToxmlObjectContainer<T> container, T value);
  
  /**
   * Called after an object has been added to the container
   * @param container the container that was modified
   * @param value the value that was added
   */
  public void added(ToxmlObjectContainer<T> container, T value);
}

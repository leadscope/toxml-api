/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

/**
 * A listener to updates to a composite object
 */
public interface CompositeListener {
  /**
   * Called after a composite value has been updated
   * @param composite the composite object being modified
   * @param tag the tag of the child value
   * @param oldValue the old value that had previously been assigned to the given tag
   * @param newValue the new value that is now assigned to the given tag
   */
  public void valueChanged(
      CompositeToxmlObject composite, 
      String tag,
      ToxmlObject oldValue,
      ToxmlObject newValue);  
}

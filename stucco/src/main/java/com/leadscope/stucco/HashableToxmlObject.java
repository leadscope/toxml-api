/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

/**
 * Interface indicating that this toxml object has implemented hashCode() and equals()
 * in a compatible way and can be stored in a ToxmlObjectSet.
 */
public interface HashableToxmlObject extends ToxmlObject {
  /**
   * @param other the other object to compare with
   * @return true iff the other object is equivalent with this one for the purposes
   * of storing in a ToxmlObjectSet
   */
  public boolean equals(Object other);

  /**
   * @return a hash code that only uses members also utilized in equals()
   */
  public int hashCode();
}

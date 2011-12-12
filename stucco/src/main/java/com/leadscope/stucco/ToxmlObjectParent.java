/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

import java.util.List;

/**
 * A container or composite toxml object - i.e. one that contains children
 */
public interface ToxmlObjectParent extends ToxmlObject {
  /**
   * Returns a new list containing all child stored under this parent
   * @return a new list of the children
   */
  public List<ToxmlObject> getChildren();
}

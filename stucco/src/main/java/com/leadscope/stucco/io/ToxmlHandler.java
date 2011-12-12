/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.io;

import com.leadscope.stucco.ToxmlObject;

/**
 * An interface that can be handed toxml objects for processing
 */
public interface ToxmlHandler<T extends ToxmlObject> {
  public void handle(T obj) throws Exception;
}

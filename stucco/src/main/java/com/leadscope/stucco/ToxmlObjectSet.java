/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

/**
 * A set of toxml objects of a specific type. The values should correctly implement
 * equals and hashCode, such that only unique values will appear in the set
 */
public interface ToxmlObjectSet<T extends HashableToxmlObject> extends ToxmlObjectContainer<T> {

}

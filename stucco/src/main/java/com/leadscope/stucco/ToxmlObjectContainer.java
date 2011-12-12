/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

import java.util.List;

/**
 * A container of toxml objects - could be an ordered list or unordered set
 */
public interface ToxmlObjectContainer<T extends ToxmlObject> extends ToxmlObjectParent {
  /**
   * Adds a listener to this container.  Listeners will be notified
   * in reverse of the order in which they were added here (as per Swing convention).
   * <p>These listeners are held with hard references - so if you want your listener
   * to be garbage collectable while this composite is still being referenced,
   * you'll need to call removeListener(listener).
   */
  public void addListener(ContainerListener<T> listener);
  
  /**
   * Removes a listener for updates to this container. Call this method when you
   * no longer want the listener to receive updates - and/or when you
   * want the listener to be garbage collected, but this composite is still being
   * referenced.
   * @param listener the listener to add
   */
  public void removeListener(ContainerListener<T> listener);
  
  /**
   * Gets the tag under which the child objects are stored
   * @return the child tag
   */
  public String getChildTag();
  
  /**
   * Gets the class of the child objects
   * @return the child class
   */
  public Class<T> getChildClass();
  
  /**
   * Adds a child to this container
   * @param child the new child to add
   */
  public void addChild(T child);
  
  /**
   * Creates a new child and adds it to the container - this method should generally only be
   * used for creating non-primitive values
   * @return the newly created value that was added
   */
  public T addNew();
  
  /**
   * Removes the given child from this container. Removes the first occurence of the child that
   * equals() to this child. 
   * @param child the child to remove
   * @return true iff the child was found and removed
   */
  public boolean removeChild(T child);
  
  /**
   * Gets the children currently stored in this container
   */
  public List<T> getValues();
  
  /**
   * Gets the ith element from the list
   * @param i the index
   * @return the value at the given index
   */
  public T get(int i);
  
  /**
   * Gets the number of children currently stored in this container
   */
  public int size();
}

/**
 * Stucco ToxML API - a Java interface for parsing and creating ToxML documents
 * Copyright (C) 2011 Scott Miller - Leadscope, Inc.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.leadscope.stucco;

import java.util.List;

/**
 * A toxml object that is composed of a set of tagged child objects
 */
public interface CompositeToxmlObject extends ToxmlObjectParent {  
  /**
   * Gets the list of valid child tags that can be used with getChild
   * @return the list of string child tags
   */
  public List<String> getChildTags();
  
  /**
   * Gets the child value for the given tag
   * @param tag the tag of the child object
   * @return the value assigned to the give tag; null if not assigned
   */
  public ToxmlObject getChild(String tag);
  
  /**
   * Sets the value of the child - also updates the parent for the given child
   * @param tag the tag under which to store
   * @param child the value of the child
   * @exception IllegalArgumentException if the tag is not valid
   * @exception IllegalArgumentException if value has already been assigned to another parent
   * @exception ClassCastException if the child is of the wrong type
   */
  public void setChild(String tag, ToxmlObject value);
  
  /**
   * Gets the class of the child that should appear under the given tag
   * @param tag the tag of the child class to get
   * @return the child class
   */
  public Class<? extends ToxmlObject> getChildClass(String tag);
  
  /**
   * Adds a listener for updates to this composite. Listeners will be notified
   * in reverse of the order in which they were added here (as per Swing convention).
   * <p>These listeners are held with hard references - so if you want your listener
   * to be garbage collectable while this composite is still being referenced,
   * you'll need to call removeListener(listener).
   * @param listener the listener to add
   */
  public void addListener(CompositeListener listener);
  
  /**
   * Removes a listener for updates to this composite. Call this method when you
   * no longer want the listener to receive updates - and/or when you
   * want the listener to be garbage collected, but this composite is still being
   * referenced.
   * @param listener the listener to add
   */
  public void removeListener(CompositeListener listener);
}

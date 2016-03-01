/**
 * Stucco ToxML API - a Java interface for parsing and creating ToxML documents
 * Copyright (C) 2016 Scott Miller - Leadscope, Inc.
 *
 * Leadscope, Inc. licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
  List<String> getChildTags();
  
  /**
   * Gets the child value for the given tag
   * @param tag the tag of the child object
   * @return the value assigned to the give tag; null if not assigned
   */
  ToxmlObject getChild(String tag);
  
  /**
   * Sets the value of the child - also updates the parent for the given child
   * @param tag the tag under which to store
   * @param child the value of the child
   * @exception IllegalArgumentException if the tag is not valid
   * @exception IllegalArgumentException if value has already been assigned to another parent
   * @exception ClassCastException if the child is of the wrong type
   */
  void setChild(String tag, ToxmlObject value);
  
  /**
   * Gets the class of the child that should appear under the given tag
   * @param tag the tag of the child class to get
   * @return the child class
   */
  Class<? extends ToxmlObject> getChildClass(String tag);
  
  /**
   * Adds a listener for updates to this composite. Listeners will be notified
   * in reverse of the order in which they were added here (as per Swing convention).
   * <p>These listeners are held with hard references - so if you want your listener
   * to be garbage collectable while this composite is still being referenced,
   * you'll need to call removeListener(listener).
   * @param listener the listener to add
   */
  void addListener(CompositeListener listener);
  
  /**
   * Removes a listener for updates to this composite. Call this method when you
   * no longer want the listener to receive updates - and/or when you
   * want the listener to be garbage collected, but this composite is still being
   * referenced.
   * @param listener the listener to add
   */
  void removeListener(CompositeListener listener);
}

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

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Interface for any object found in the toxml hierarchy
 */
public interface ToxmlObject {
  /**
   * ToxML elements need to be camel cased (starting with a capital letter) and
   * can only contain letters an digits
   */
  Pattern validTagPattern = Pattern
      .compile("^(?!XML)[A-Z][a-zA-Z0-9]*");
  
  /**
   * Source strings cannot contain commas
   */
  Pattern validSourcePattern = Pattern
      .compile("[^,]+");
  
  /**
   * Gets the parent of this object
   * @return the parent containing this object; null if this is the root or if
   * the object is not attached to a toxml hierarchy
   */
  ToxmlObjectParent getParent();
  
  /**
   * Sets the parent of this object
   * @param parent the parent for this object - should be a container or composite that
   * holds this object
   */
  void setParent(ToxmlObjectParent parent);
  
  /**
   * Gets the list of values by path. An empty list is returned if any
   * of the objects are null or empty lists along the path
   * @param path the relative path from this object - null indicates to return this object
   * @return the list of values referred to by the path
   * @exception IllegalArgumentException if any of the tags in the path are
   * invalid from this object
   */
  List<ToxmlObject> getValuesByPath(ToxmlPath path) throws IllegalArgumentException;
  
  /**
   * Checks to see if the given path results in a single object that is equals() to
   * the passed in parameter
   * @param path the relative path from this object - null indicates to return this object
   * @value the value to compare - null ok and will only return true iff no objects match the path
   * @return true iff the given value matches the result of the path
   * @exception IllegalArgumentExcepiton if any of the tags in the path are invalid
   * from this object
   */
  boolean valueByPathEquals(ToxmlPath path, ToxmlObject value) throws IllegalArgumentException;

  /**
   * Checks to see if the given path results in a single object that is equals() to
   * the passed in parameter
   * @param path the relative path from this object - null indicates to return this object
   * @value the value to compare - null ok and will only return true iff no objects match the path
   * @return true iff the given value matches the result of the path
   * @exception IllegalArgumentExcepiton if any of the tags in the path are invalid
   * from this object
   */
  boolean valueByPathEquals(ToxmlPath path, String value) throws IllegalArgumentException;

  /**
   * Sets the sources for this object 
   * @param sources the set of source strings - each source string must be a 
   * validSourcePattern
   */
  void setSources(Collection<String> sources) throws IllegalArgumentException;
  
  /**
   * Adds a source to this object
   * @param source the source to add - must be validSourcePattern
   */
  void addSource(String source) throws IllegalArgumentException;
  
  /**
   * Gets the list of sources for this object
   * @return the sorted list of sources for this object
   */
  List<String> getSources();
  
  /**
   * Determines if this object is considered empty and could be omitted
   * from the serialized xml
   */
  boolean isEmpty();
  
  /**
   * Sets the difference status for this object
   * @param status the status; null indicates that the object is present in both
   * source documents
   */
  void setDiffStatus(DiffStatus status);
  
  /**
   * Gets the difference status for this object
   * @return the status; null indicates that the object is present in both
   * source documents
   */
  DiffStatus getDiffStatus();
  
  /**
   * Specifies what was the original value in the source document. This is
   * a simple string which should contain enough information to demonstrate
   * the difference
   * @param originalConflictValue the original value
   */
  void setOriginalConflictValue(String originalConflictValue);
  
  /**
   * Gets the original value in the source document.
   * @return a simple string that represents the original value; null if
   * there was no conflict
   */
  String getOriginalConflictValue();
  
  /**
   * Clears everything regarding the difference status on this object
   */
  void clearDiffStatus();
  
  /**
   * Determines if this object or any of its children contains a difference
   * Default implementation just checks the status on this object - containers
   * will need to search children
   * @return true iff a difference exists within this object
   */
  boolean containsDiff();

  /**
   * Determines if this object or any of its children contains a conflict
   * from a differencing
   * Default implementation just checks the status on this object - containers
   * will need to search children
   * @return true iff a conflict exists within this object
   */
  boolean containsConflict();
  
  /**
   * Accepts the toxml visitor
   * @param visitor the visitor to accept
   */
  <V> V accept(ToxmlVisitor<V> visitor) throws Exception;
}

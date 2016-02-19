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

import java.util.ArrayList;
import java.util.List;

/**
 * An XML path that can be used to access values from a hierarchical toxml object 
 */
public class ToxmlPath {
  public static final String DELIMITER = "/";
  public static final String PARENT_ELEMENT = "..";
  
  private String path;
  private List<String> tags;
  
  public static boolean validTag(String tag) {
    return PARENT_ELEMENT.equals(tag) || 
        ToxmlObject.validTagPattern.matcher(tag).matches();
  }
  
  private ToxmlPath() { }
  
  /**
   * @param tags the list of tags that should be included in the path
   * @exception IllegalArgumentException if any of the tags are invalid
   */
  public ToxmlPath(List<String> tags) {
    if (tags.size() == 0) {
      throw new IllegalArgumentException("Cannot create empty path");
    }
    
    StringBuilder sb = new StringBuilder();
    for (String tag : tags) {
      if (!validTag(tag)) {
        throw new IllegalArgumentException(tag + " is an invalid toxml element tag");
      }
      if (sb.length() > 0) {
        sb.append(DELIMITER);
      }
      sb.append(tag);
    }
    path = sb.toString();
  }
  
  /**
   * @param path the / delimited path
   * @exception IllegalArgumentException if  
   */
  public ToxmlPath(String path) {
    if (path.length() == 0) {
      throw new IllegalArgumentException("Cannot create empty path");
    }
    tags = new ArrayList<String>();
    for (String tag : path.split(DELIMITER)) {
      if (!validTag(tag)) {
        throw new IllegalArgumentException(tag + " is an invalid toxml element tag");
      }
      tags.add(tag);
    }
  }
  
  public String toString() {
    return path;
  }

  /**
   * Determines if the first element is the parent element
   * @return true iff the first element is the parent
   */
  public boolean isParentFirst() {
    return tags.size() > 0 && tags.get(0).equals(PARENT_ELEMENT);
  }
  
  /**
   * Gets rest of the path after the first element
   * @return the rest of the path - null if there is only one element in this path
   */
  public ToxmlPath tail() {
    if (tags.size() == 1) {
      return null;
    }
    return new ToxmlPath(tags.subList(1, tags.size()));
  }
  
  /**
   * Gets the first element in this path
   * @return the first element
   */
  public String first() {
    return tags.get(0);
  }
  
  /**
   * Creates a new path with the next tag appended
   * @param tag the tag to append
   * @return a new path with the appended tag
   */
  public ToxmlPath append(String tag) {
    if (!validTag(tag)) {
      throw new IllegalArgumentException(tag + " is an invalid toxml element tag");
    }
    
    ToxmlPath newPath = new ToxmlPath();
    newPath.tags = new ArrayList<String>(this.tags);
    newPath.tags.add(tag);
    newPath.path = this.path + DELIMITER + tag;
    return newPath;
  }

}

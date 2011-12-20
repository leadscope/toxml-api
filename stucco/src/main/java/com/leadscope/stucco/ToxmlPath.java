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

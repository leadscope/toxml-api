/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
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
}

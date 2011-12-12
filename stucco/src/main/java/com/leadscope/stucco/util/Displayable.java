package com.leadscope.stucco.util;

/**
 * Interface for objects that can be presented in a simple, displayable string
 */
public interface Displayable {
  /**
   * Provides a viewable string representation of this object - never should be null
   * @return a string representation of this object that is suitable for viewing
   */
  public String toDisplayableString();
}

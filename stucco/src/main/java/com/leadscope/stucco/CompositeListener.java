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

/**
 * A listener to updates to a composite object
 */
public interface CompositeListener {
  /**
   * Called after a composite value has been updated
   * @param composite the composite object being modified
   * @param tag the tag of the child value
   * @param oldValue the old value that had previously been assigned to the given tag
   * @param newValue the new value that is now assigned to the given tag
   */
  public void valueChanged(
      CompositeToxmlObject composite, 
      String tag,
      ToxmlObject oldValue,
      ToxmlObject newValue);  
}

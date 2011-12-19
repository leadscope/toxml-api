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
 * Interface indicating that this toxml object has implemented hashCode() and equals()
 * in a compatible way and can be stored in a ToxmlObjectSet.
 */
public interface HashableToxmlObject extends ToxmlObject {
  /**
   * @param other the other object to compare with
   * @return true iff the other object is equivalent with this one for the purposes
   * of storing in a ToxmlObjectSet
   */
  public boolean equals(Object other);

  /**
   * @return a hash code that only uses members also utilized in equals()
   */
  public int hashCode();
}

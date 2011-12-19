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
 * Indicates the status of an object in regards to the difference
 * of two different lsml documents
 */
public enum DiffStatus {
  ADDED("added"),
  REMOVED("removed"),
  CONFLICT("conflict");
  
  private String value;
  private DiffStatus(String value) {
    this.value = value;
  }
  
  public String toString() {
    return value;
  }
  
  public static DiffStatus parse(String s) {
    if (s == null) {
      return null;
    }
    s = s.trim().toLowerCase();
    if (s.length() == 0) {
      return null;
    }
    
    for (DiffStatus status : DiffStatus.values()) {
      if (status.value.equals(s)) {
        return status;
      }
    }
    
    throw new RuntimeException("Unknown difference status: " + s);
  }
}

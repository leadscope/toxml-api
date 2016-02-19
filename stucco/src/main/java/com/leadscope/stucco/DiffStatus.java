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

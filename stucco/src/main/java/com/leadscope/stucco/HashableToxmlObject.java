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

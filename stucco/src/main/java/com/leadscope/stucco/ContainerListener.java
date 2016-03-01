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
 * A listener to a ToxmlObjectContainer - will be notified when an object is added or
 * removed
 */
public interface ContainerListener<T extends ToxmlObject> {
  /**
   * Called after an object is removed from the container
   * @param container the container that was modified
   * @param value the value that was removed
   */
  void removed(ToxmlObjectContainer<T> container, T value);
  
  /**
   * Called after an object has been added to the container
   * @param container the container that was modified
   * @param value the value that was added
   */
  void added(ToxmlObjectContainer<T> container, T value);
}

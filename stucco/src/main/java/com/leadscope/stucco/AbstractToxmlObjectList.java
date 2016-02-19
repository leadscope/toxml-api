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
 * Abstract implementation of an object list
 */
public abstract class AbstractToxmlObjectList<T extends ToxmlObject> 
    extends AbstractToxmlObjectContainer<T> implements ToxmlObjectList<T> {
  
  public void addChild(T child) {
    if (child.getParent() != null) {
      throw new IllegalArgumentException("child has already been added to another parent");
    }
    values.add(child);
    child.setParent(this);
    childAdded(child);
  }
  
  public <V> V accept(ToxmlVisitor<V> visitor) throws Exception {
    return visitor.visit(this);
  }
}

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
public abstract class AbstractToxmlObjectSet<T extends HashableToxmlObject> 
     extends AbstractToxmlObjectContainer<T> implements ToxmlObjectSet<T> {
  
  /**
   * Only adds the child if another child is not present. However, a listener event
   * will be fired in any case.
   */
  public void addChild(T child) {
    if (child.getParent() != null) {
      throw new IllegalArgumentException("child has already been added to another parent");
    }
    
    T alreadyPresentChild = null;
    for (T value : values) {
      if (value.equals(child)) {
        for (String source : child.getSources()) {
          value.addSource(source);
        }
        alreadyPresentChild = value;
        break;
      }
    }
    
    if (alreadyPresentChild == null) {
      values.add(child);
      child.setParent(this);
      childAdded(child);
    }
    else {
      childAdded(alreadyPresentChild);
    }    
  }

  public <V> V accept(ToxmlVisitor<V> visitor) throws Exception {
    return visitor.visit(this);
  }
}

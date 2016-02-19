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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The abstract parent class of all composite toxml objects. Handles listeners, visitors and
 * path-based accessors. Subclasses will provide accessors for each member, that ensure the
 * new value has not been assigned to another object, update the parent values and should call
 * updatedValue(tag, oldValue, newValue) when complete.
 */
public abstract class AbstractCompositeToxmlObject extends AbstractToxmlObject implements CompositeToxmlObject {
  private List<CompositeListener> listeners;
  
  /**
   * This method should be called after every update. This abstract implementation notifies
   * any listeners that have been assigned. Anyone overriding this method should be sure
   * to call super.
   * @param tag the tag of the value being updated
   * @param oldValue the value that was previously assigned to the given tag
   * @param newValue the new value that was just assigned to the given tag
   */
  protected void updatedValue(String tag, ToxmlObject oldValue, ToxmlObject newValue) {
    if (listeners != null) {
      List<CompositeListener> notifying = new ArrayList<CompositeListener>(listeners);
      for (int i = notifying.size()-1; i >= 0; i--) {
        notifying.get(i).valueChanged(this, tag, oldValue, newValue);
      }
    }
  }
  
  public void addListener(CompositeListener listener) {
    if (listeners == null) {
      listeners = new ArrayList<CompositeListener>();
    }
    listeners.add(listener);
  }
  
  public void removeListener(CompositeListener listener) {
    if (listeners != null) {
      listeners.remove(listener);
      if (listeners.size() == 0) {
        listeners = null;
      }
    }
  }
  
  public final boolean isEmpty() {
    for (String tag : getChildTags()) {
      ToxmlObject child = getChild(tag);
      if (child != null && !child.isEmpty()) {
        return false;
      }
    }
    return true;
  }
  
  public final List<ToxmlObject> getChildren() {
    List<ToxmlObject> children = new ArrayList<ToxmlObject>();
    for (String tag : getChildTags()) {
      ToxmlObject child = getChild(tag);
      if (child != null) {
        children.add(child);
      }
    }
    return children;
  }

  public final List<ToxmlObject> getValuesByPath(ToxmlPath path) throws IllegalArgumentException {
    if (path == null) {
      return Arrays.asList((ToxmlObject)this);
    }
    ToxmlObject next = path.isParentFirst() ? getParent() : getChild(path.first());
    if (next == null) {
      return new ArrayList<ToxmlObject>(0);
    }
    return next.getValuesByPath(path.tail());
  }
  
  public final <V> V accept(ToxmlVisitor<V> visitor) throws Exception {
    return visitor.visit(this);
  }
}

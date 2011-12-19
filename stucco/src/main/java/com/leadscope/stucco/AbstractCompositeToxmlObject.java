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

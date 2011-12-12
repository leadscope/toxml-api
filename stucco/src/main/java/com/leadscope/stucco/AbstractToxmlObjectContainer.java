/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract parent of sets and lists of toxml objects
 */
public abstract class AbstractToxmlObjectContainer<T extends ToxmlObject> 
  extends AbstractToxmlObject implements ToxmlObjectContainer<T> {
  
  protected List<T> values = new ArrayList<T>();
  
  private List<ContainerListener<T>> listeners;
  
  public void addListener(ContainerListener<T> listener) {
    if (listeners == null) {
      listeners = new ArrayList<ContainerListener<T>>();
    }
    listeners.add(listener);
  }
  
  public void removeListener(ContainerListener<T> listener) {
    if (listeners != null) {
      listeners.remove(listener);
      if (listeners.size() == 0) {
        listeners = null;
      }
    }
  }

  public T addNew() {
    try {
      T newChild = getChildClass().newInstance();
      addChild(newChild);
      return newChild;
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * This method should be called after a child has been removed. This implementation handles
   * the listeners. Subclasses that override should call super.
   * @param child the child that has been removed
   */
  protected void childRemoved(T child) {
    if (listeners != null) {
      List<ContainerListener<T>> notifying = new ArrayList<ContainerListener<T>>();
      for (int i = notifying.size()-1; i >=0; i--) {
        notifying.get(i).removed(this, child);
      }
    }
  }

  /**
   * This method should be called after a child has been added. This implementation handles
   * the listeners. Subclasses that override should call super.
   * @param child the child that has been removed
   */
  protected void childAdded(T child) {
    if (listeners != null) {
      List<ContainerListener<T>> notifying = new ArrayList<ContainerListener<T>>();
      for (int i = notifying.size()-1; i >=0; i--) {
        notifying.get(i).added(this, child);
      }
    }
  }

  public boolean removeChild(T child) {
    for (int i = 0; i < values.size(); i++) {
      T nextChild = values.get(i);
      if (nextChild.equals(child)) {
        values.remove(i);
        nextChild.setParent(null);
        childRemoved(nextChild);
        return true;
      }
    }
    return false;
  }

  public boolean isEmpty() {
    return values.isEmpty();
  }
  
  public int size() {
    return values.size();
  }

  public T get(int i) {
    return values.get(i);
  }

  public List<ToxmlObject> getChildren() {
    return new ArrayList<ToxmlObject>(values);
  }

  public List<ToxmlObject> getValuesByPath(ToxmlPath path) throws IllegalArgumentException {
    if (path == null) {
      return Arrays.asList((ToxmlObject)this);
    }
    
    if (path.isParentFirst()) {
      ToxmlObject parent = getParent();
      if (parent == null) {
        return new ArrayList<ToxmlObject>(0);
      }
      else {
        return parent.getValuesByPath(path.tail());
      }
    }
    else {
      String tag = path.first();
      if (!tag.equals(getChildTag())) {
        throw new IllegalArgumentException("Unknown tag: " + tag);
      }
      List<ToxmlObject> results = new ArrayList<ToxmlObject>();
      ToxmlPath tail = path.tail();
      for (T value : values) {
        if (tail == null) {
          results.add(value);
        }
        else {
          results.addAll(value.getValuesByPath(tail));
        }
      }    
      return results;
    }
  }

  public List<T> getValues() {
    return values;
  }
}
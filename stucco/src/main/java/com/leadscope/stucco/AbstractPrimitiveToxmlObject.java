/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPrimitiveToxmlObject 
  extends AbstractToxmlObject implements PrimitiveToxmlObject {
  
  public List<ToxmlObject> getValuesByPath(ToxmlPath path) throws IllegalArgumentException {
    if (path == null) {
      List<ToxmlObject> list = new ArrayList<ToxmlObject>();
      list.add(this);
      return list;
    }
    else if (path.isParentFirst()) {
      ToxmlObject parent = getParent();
      if (parent != null) {
        return parent.getValuesByPath(path.tail());
      }
      else {
        return new ArrayList<ToxmlObject>(0);
      }
    }
    else {
      throw new IllegalArgumentException("Primitive object does not contain any children");
    }
  }
  
  public boolean isEmpty() {
    return false;
  }
}

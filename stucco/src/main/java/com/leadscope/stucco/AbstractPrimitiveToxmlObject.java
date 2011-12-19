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

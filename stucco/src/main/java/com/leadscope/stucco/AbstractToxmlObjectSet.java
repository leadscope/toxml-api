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

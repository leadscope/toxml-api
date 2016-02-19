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

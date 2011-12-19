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

import java.util.*;

/**
 * Implements some common functionality for toxml objects
 */
public abstract class AbstractToxmlObject implements ToxmlObject {
  private Set<String> sources;
  
  private DiffStatus diffStatus;
  private String originalConflictValue;
  
  private ToxmlObjectParent parent;
  
  public final void setParent(ToxmlObjectParent parent) {
    this.parent = parent;
  }
  
  public final ToxmlObjectParent getParent() {
    return parent;
  }
  
  public final boolean valueByPathEquals(ToxmlPath path, ToxmlObject value) {
    List<ToxmlObject> values = getValuesByPath(path);
    if (value == null) {
      return values.size() == 0;
    }
    if (values.size() == 1) {
      ToxmlObject obj = values.get(0);
      return value.equals(obj);
    }
    return false;
  }

  public final boolean valueByPathEquals(ToxmlPath path, String value) {
    if (value == null) {
      return valueByPathEquals(path, (ToxmlObject)null);
    }
    else {
      return valueByPathEquals(path, new StringValue(value));
    }
  }
  
  public final void setSources(Collection<String> sources) throws IllegalArgumentException {
    Set<String> newSources = new HashSet<String>();    
    for (String source : sources) {
      source = source.trim();
      if (!validSourcePattern.matcher(source).matches()) {
        throw new IllegalArgumentException(source + " is an invalid source string");
      }
      newSources.add(source);
    }
    this.sources = newSources;
  }
  
  public final void addSource(String source) throws IllegalArgumentException {
    if (sources == null) {
      sources = new HashSet<String>();
    }
    source = source.trim();
    if (!validSourcePattern.matcher(source).matches()) {
      throw new IllegalArgumentException(source + " is an invalid source string");
    }
    sources.add(source);
  }

  public final List<String> getSources() {
    if (sources == null) {
      return new ArrayList<String>();
    }
    List<String> sortedSources = new ArrayList<String>(sources);
    Collections.sort(sortedSources);
    return sortedSources;
  }
  
  public final void setDiffStatus(DiffStatus status) {
    this.diffStatus = status;
  }
  
  public final DiffStatus getDiffStatus() {
    return diffStatus;
  }
  
  public final void setOriginalConflictValue(String originalConflictValue) {
    this.originalConflictValue = originalConflictValue;
  }
  
  public final String getOriginalConflictValue() {
    return originalConflictValue;
  }
  
  public final void clearDiffStatus() {
    this.originalConflictValue = null;
    this.diffStatus = null;
  }
  
  public final boolean containsDiff() {
    return diffStatus != null;
  }

  public final boolean containsConflict() {
    return diffStatus == DiffStatus.CONFLICT;
  }
}

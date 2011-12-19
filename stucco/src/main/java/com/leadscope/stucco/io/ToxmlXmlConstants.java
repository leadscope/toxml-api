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
package com.leadscope.stucco.io;

/**
 * Interface defining the common tags and attributes use in the ToxML XML serialization
 * and deserialization
 */
public interface ToxmlXmlConstants {
  // namespace of toxml - currently none
  public static final String namespace = null;
  
  // common attributes
  public static final String SOURCES_ATTRIBUTE = "sources";
  public static final String DIFF_ATTRIBUTE = "diffStatus";
  public static final String ORIGINAL_CONFLICT_ATTRIBUTE = "originalConflictValue";

  // range and inexact value attributes and tags
  public static final String QUALIFIER_ATTRIBUTE = "qualifier";
  public static final String GREATER_ATTRIBUTE = "greater";
  public static final String LESS_ATTRIBUTE = "less";
  public static final String UNKNOWN_ATTRIBUTE = "unknown";
  public static final String LOW_VALUE_TAG = "LowValue";
  public static final String HIGH_VALUE_TAG = "HighValue";
  
  // typed value attribute
  public static final String TYPE_ATTRIBUTE = "type";
  
  
}

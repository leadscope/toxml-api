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

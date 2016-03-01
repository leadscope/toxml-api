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
  String namespace = null;
  
  // common attributes
  String SOURCES_ATTRIBUTE = "sources";
  String DIFF_ATTRIBUTE = "diffStatus";
  String ORIGINAL_CONFLICT_ATTRIBUTE = "originalConflictValue";

  // range and inexact value attributes and tags
  String QUALIFIER_ATTRIBUTE = "qualifier";
  String GREATER_ATTRIBUTE = "greater";
  String LESS_ATTRIBUTE = "less";
  String UNKNOWN_ATTRIBUTE = "unknown";
  String LOW_VALUE_TAG = "LowValue";
  String HIGH_VALUE_TAG = "HighValue";
  
  // typed value attribute
  String TYPE_ATTRIBUTE = "type";
}

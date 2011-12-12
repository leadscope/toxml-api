/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
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

/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
package com.leadscope.stucco.builder;

/**
 * Contains information regarding the toxml specification export
 */
public class Spec {

  // XML Schema element names
  public static final String CASE_SENSITIVE = "caseSensitive";
  public static final String DEPENDENT_FIELD = "dependentField";
  public static final String ELEMENT = "element";
  public static final String ELEMENT_ID = "elementId";
  public static final String ENUMERATED_VALUES = "enumeratedValues";
  public static final String FRACTION_OF_BASE = "fractionOfBase";
  public static final String MAGNITUDE = "magnitude";
  public static final String METRIC = "metric";
  public static final String NAME = "name";
  public static final String PREFERRED_UNITS = "preferredUnits";
  public static final String SAMPLE = "sample";
  public static final String SCHEMA = "schema";
  public static final String SCHEMA_CODE = "schemaCode";
  public static final String STATUS = "status";
  public static final String DESCRIPTION = "description";
  public static final String ROOT_TYPE = "rootType";
  public static final String TAG = "tag";
  public static final String TERM = "term";
  public static final String TYPE = "type";
  public static final String VALUE = "value";
  public static final String VOCABULARY = "vocabulary";
  
  // root types
  public static final String STUDY_LIST_ROOT_TYPE = "STUDY_LIST";
  public static final String COMPOUND_ROOT_TYPE = "COMPOUND";
  
  // common tags
  public static final String TOXICITY_STUDIES_TAG = "ToxicityStudies";
  public static final String TEST_LIST_TAG = "Tests";
  public static final String TREATMENT_LIST_TAG = "Treatments";
  public static final String TREATMENT_TAG = "TreatmentGroup";
  public static final String POSITIVE_CONTROL_LIST_TAG = "PositiveTestControls";
  public static final String NEGATIVE_CONTROL_LIST_TAG = "NegativeTestControls";
    
  // built-in type names
  public static final String COMPOSITE = "Composite";
  public static final String SET = "Set";
  public static final String LIST = "List";
  
  public static final String STUDY_TREATMENT = "StudyTreatment";
  
  // built-in primitive types
  public static final String BOOLEAN = "Boolean";
  public static final String CDATA = "CData";
  public static final String DATE = "Date";
  public static final String FLOAT = "Float";
  public static final String INEXACT_VALUE = "InexactValue";
  public static final String INTEGER = "Integer";
  public static final String INTEGER_RANGE = "IntegerRange";
  public static final String INTEGER_ARRAY = "IntegerArray";
  public static final String QUANTITY = "Quantity";
  public static final String STRING = "String";
  public static final String TYPED_VALUE = "TypedValue";
  public static final String UNITS = "Units";
}

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

public interface TestCondition extends CompositeToxmlObject {
  StringValue getControlComments();
  String getControlCommentsValue();
  void setControlComments(StringValue controlComments);
  void setControlCommentsValue(String controlComments);

  StringValue getExperimentId();
  String getExperimentIdValue();
  void setExperimentId(StringValue experimentId);
  void setExperimentIdValue(String experimentId);

  IntegerValue getNumberDose();
  Integer getNumberDoseValue();
  void setNumberDose(IntegerValue numberDose);
  void setNumberDoseValue(Integer numberDose);

  IntegerValue getNumberPerTreatmentGroup();
  Integer getNumberPerTreatmentGroupValue();
  void setNumberPerTreatmentGroup(IntegerValue numberPerTreatmentGroup);
  void setNumberPerTreatmentGroupValue(Integer numberPerTreatmentGroup);

  StringValue getPreTestComments();
  String getPreTestCommentsValue();
  void setPreTestComments(StringValue preTestComments);
  void setPreTestCommentsValue(String preTestComments);

  StringValue getReferenceCompound();
  String getReferenceCompoundValue();
  void setReferenceCompound(StringValue referenceCompound);
  void setReferenceCompoundValue(String referenceCompound);

  Quantity getReferenceCompoundDose();
  void setReferenceCompoundDose(Quantity referenceCompoundDose);

  StringValue getRegulatoryTestType();
  String getRegulatoryTestTypeValue();
  void setRegulatoryTestType(StringValue regulatoryTestType);
  void setRegulatoryTestTypeValue(String regulatoryTestType);

  StringValue getSolubility();
  String getSolubilityValue();
  void setSolubility(StringValue solubility);
  void setSolubilityValue(String solubility);

  ToxmlObjectList<? extends SolventVehicleSubstanceQuantity> getSolventVehicle();

  StringValue getTestConditionComments();
  String getTestConditionCommentsValue();
  void setTestConditionComments(StringValue testConditionComments);
  void setTestConditionCommentsValue(String testConditionComments);

  StringValue getTestDoseComments();
  String getTestDoseCommentsValue();
  void setTestDoseComments(StringValue testDoseComments);
  void setTestDoseCommentsValue(String testDoseComments);

  StringValue getTestId();
  String getTestIdValue();
  void setTestId(StringValue testId);
  void setTestIdValue(String testId);

}

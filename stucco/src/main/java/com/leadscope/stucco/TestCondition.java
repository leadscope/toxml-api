/**
 * Stucco ToxML API - a Java interface for parsing and creating ToxML documents
 * Copyright (C) 2016 Scott Miller - Leadscope, Inc.
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

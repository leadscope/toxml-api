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

import com.leadscope.stucco.model.ControlType;
import com.leadscope.stucco.model.DosageRegimen;

public interface StudyTreatment extends CompositeToxmlObject {
  StringValue getCoTreatmentCompound();
  String getCoTreatmentCompoundValue();
  void setCoTreatmentCompound(StringValue coTreatmentCompound);
  void setCoTreatmentCompoundValue(String coTreatmentCompound);

  StringValue getCoTreatmentDose();
  String getCoTreatmentDoseValue();
  void setCoTreatmentDose(StringValue coTreatmentDose);
  void setCoTreatmentDoseValue(String coTreatmentDose);

  StringValue getCoTreatmentSampleId();
  String getCoTreatmentSampleIdValue();
  void setCoTreatmentSampleId(StringValue coTreatmentSampleId);
  void setCoTreatmentSampleIdValue(String coTreatmentSampleId);

  ControlType getControlType();
  void setControlType(ControlType controlType);

  DosageRegimen getDosageRegimen();
  void setDosageRegimen(DosageRegimen dosageRegimen);

  StringValue getDosePeriodType();
  String getDosePeriodTypeValue();
  void setDosePeriodType(StringValue dosePeriodType);
  void setDosePeriodTypeValue(String dosePeriodType);

  StringValue getIndividualSubjectCount();
  String getIndividualSubjectCountValue();
  void setIndividualSubjectCount(StringValue individualSubjectCount);
  void setIndividualSubjectCountValue(String individualSubjectCount);

  Quantity getInterimChangeTime();
  void setInterimChangeTime(Quantity interimChangeTime);

  DosageRegimen getOriginalDosageRegimen();
  void setOriginalDosageRegimen(DosageRegimen originalDosageRegimen);

  StringValue getPreTreatmentCompound();
  String getPreTreatmentCompoundValue();
  void setPreTreatmentCompound(StringValue preTreatmentCompound);
  void setPreTreatmentCompoundValue(String preTreatmentCompound);

  StringValue getPreTreatmentDose();
  String getPreTreatmentDoseValue();
  void setPreTreatmentDose(StringValue preTreatmentDose);
  void setPreTreatmentDoseValue(String preTreatmentDose);

  StringValue getPreTreatmentSampleId();
  String getPreTreatmentSampleIdValue();
  void setPreTreatmentSampleId(StringValue preTreatmentSampleId);
  void setPreTreatmentSampleIdValue(String preTreatmentSampleId);

  Quantity getRecoveryDuration();
  void setRecoveryDuration(Quantity recoveryDuration);

  TreatmentResults getResults();
}

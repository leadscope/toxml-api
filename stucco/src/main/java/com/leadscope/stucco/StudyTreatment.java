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

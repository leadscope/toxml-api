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

public interface TreatmentResultFindings extends CompositeToxmlObject {
  StringValue getCategory();
  String getCategoryValue();
  void setCategory(StringValue category);
  void setCategoryValue(String category);

  StringValue getDescription();
  String getDescriptionValue();
  void setDescription(StringValue description);
  void setDescriptionValue(String description);

  /** temporarily removed until Subchronic incident count/rate is resolved
  StringValue getIncidentRate();
  String getIncidentRateValue();
  void setIncidentRate(StringValue incidentRate);
  void setIncidentRateValue(String incidentRate);
   */

  StringValue getPeriod();
  String getPeriodValue();
  void setPeriod(StringValue period);
  void setPeriodValue(String period);

  StringValue getRecoveryIncidentRate();
  String getRecoveryIncidentRateValue();
  void setRecoveryIncidentRate(StringValue recoveryIncidentRate);
  void setRecoveryIncidentRateValue(String recoveryIncidentRate);

  StringValue getResultFindingsComments();
  String getResultFindingsCommentsValue();
  void setResultFindingsComments(StringValue resultFindingsComments);
  void setResultFindingsCommentsValue(String resultFindingsComments);

  StringValue getSeverity();
  String getSeverityValue();
  void setSeverity(StringValue severity);
  void setSeverityValue(String severity);

  StringValue getStatisticalSignificance();
  String getStatisticalSignificanceValue();
  void setStatisticalSignificance(StringValue statisticalSignificance);
  void setStatisticalSignificanceValue(String statisticalSignificance);

  StringValue getTimeOfFindings();
  String getTimeOfFindingsValue();
  void setTimeOfFindings(StringValue timeOfFindings);
  void setTimeOfFindingsValue(String timeOfFindings);

  StringValue getTreatmentRelatedSignificance();
  String getTreatmentRelatedSignificanceValue();
  void setTreatmentRelatedSignificance(StringValue treatmentRelatedSignificance);
  void setTreatmentRelatedSignificanceValue(String treatmentRelatedSignificance);
}

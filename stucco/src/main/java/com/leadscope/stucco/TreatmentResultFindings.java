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

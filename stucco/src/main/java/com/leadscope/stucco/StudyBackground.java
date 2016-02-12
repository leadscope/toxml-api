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

import com.leadscope.stucco.model.ReferenceCompounds;
import com.leadscope.stucco.model.References;
import com.leadscope.stucco.model.TestSubstanceCharacterization;
import com.leadscope.stucco.model.TestType;

public interface StudyBackground extends CompositeToxmlObject {
  StringValue getBatchLotNumber();
  String getBatchLotNumberValue();
  void setBatchLotNumber(StringValue batchLotNumber);
  void setBatchLotNumberValue(String batchLotNumber);

  StringValue getCRONumber();
  String getCRONumberValue();
  void setCRONumber(StringValue croNumber);
  void setCRONumberValue(String croNumber);

  StringValue getCompleteness();
  String getCompletenessValue();
  void setCompleteness(StringValue completeness);
  void setCompletenessValue(String completeness);

  StringValue getConfidentiality();
  String getConfidentialityValue();
  void setConfidentiality(StringValue confidentiality);
  void setConfidentialityValue(String confidentiality);

  StringValue getContractNumber();
  String getContractNumberValue();
  void setContractNumber(StringValue contractNumber);
  void setContractNumberValue(String contractNumber);

  StringValue getDocumentNumber();
  String getDocumentNumberValue();
  void setDocumentNumber(StringValue documentNumber);
  void setDocumentNumberValue(String documentNumber);

  StringValue getDocumentStatus();
  String getDocumentStatusValue();
  void setDocumentStatus(StringValue documentStatus);
  void setDocumentStatusValue(String documentStatus);

  StringValue getDocumentType();
  String getDocumentTypeValue();
  void setDocumentType(StringValue documentType);
  void setDocumentTypeValue(String documentType);

  Quantity getDuration();
  void setDuration(Quantity duration);

  BooleanValue getFARM();
  Boolean getFARMValue();
  void setFARM(BooleanValue farm);
  void setFARMValue(Boolean farm);

  StringValue getGLPQACompliance();
  String getGLPQAComplianceValue();
  void setGLPQACompliance(StringValue glpqaCompliance);
  void setGLPQAComplianceValue(String glpqaCompliance);

  StringValue getGuideline();
  String getGuidelineValue();
  void setGuideline(StringValue guideline);
  void setGuidelineValue(String guideline);

  StringValue getNDANumber();
  String getNDANumberValue();
  void setNDANumber(StringValue ndaNumber);
  void setNDANumberValue(String ndaNumber);

  StringValue getPerformingLaboratory();
  String getPerformingLaboratoryValue();
  void setPerformingLaboratory(StringValue performingLaboratory);
  void setPerformingLaboratoryValue(String performingLaboratory);

  PhysicalCharacteristics getPhysicalCharacteristics();

  StringValue getProtocol();
  String getProtocolValue();
  void setProtocol(StringValue protocol);
  void setProtocolValue(String protocol);

  StringValue getProtocolComments();
  String getProtocolCommentsValue();
  void setProtocolComments(StringValue protocolComments);
  void setProtocolCommentsValue(String protocolComments);

  StringValue getProtocolDeviations();
  String getProtocolDeviationsValue();
  void setProtocolDeviations(StringValue protocolDeviations);
  void setProtocolDeviationsValue(String protocolDeviations);

  ReferenceCompounds getReferenceCompounds();
  void setReferenceCompounds(ReferenceCompounds referenceCompounds);

  References getReferences();
  void setReferences(References references);

  TestType getRegulatoryTestType();
  void setRegulatoryTestType(TestType regulatoryTestType);

  DateValue getReportDate();
  void setReportDate(DateValue reportDate);

  DateValue getReviewDate();
  void setReviewDate(DateValue reviewDate);

  StringValue getReviewer();
  String getReviewerValue();
  void setReviewer(StringValue reviewer);
  void setReviewerValue(String reviewer);

  StringValue getSampleNumber();
  String getSampleNumberValue();
  void setSampleNumber(StringValue sampleNumber);
  void setSampleNumberValue(String sampleNumber);

  DateValue getStudyEndDate();
  void setStudyEndDate(DateValue studyEndDate);

  StringValue getStudyLocation();
  String getStudyLocationValue();
  void setStudyLocation(StringValue studyLocation);
  void setStudyLocationValue(String studyLocation);

  StringValue getStudyReportNumber();
  String getStudyReportNumberValue();
  void setStudyReportNumber(StringValue studyReportNumber);
  void setStudyReportNumberValue(String studyReportNumber);

  DateValue getStudyStartDate();
  void setStudyStartDate(DateValue studyStartDate);

  StringValue getStudyTitle();
  String getStudyTitleValue();
  void setStudyTitle(StringValue studyTitle);
  void setStudyTitleValue(String studyTitle);

  BooleanValue getSummaryData();
  Boolean getSummaryDataValue();
  void setSummaryData(BooleanValue summaryData);
  void setSummaryDataValue(Boolean summaryData);

  TestSubstanceCharacterization getTestSubstanceCharacterization();
  void setTestSubstanceCharacterization(TestSubstanceCharacterization testSubstanceCharacterization);
}

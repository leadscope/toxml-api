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

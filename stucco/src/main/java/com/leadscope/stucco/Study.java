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

import com.leadscope.stucco.model.EntryInformation;
import com.leadscope.stucco.model.StudyCalls;

public interface Study extends CompositeToxmlObject {
  StudyBackground getBackground();

  EntryInformation getEntryInformation();
  void setEntryInformation(EntryInformation entryInformation);

  StringValue getLeadscopeStudyId();
  String getLeadscopeStudyIdValue();
  void setLeadscopeStudyId(StringValue leadscopeStudyId);
  void setLeadscopeStudyIdValue(String leadscopeStudyId);

  StringValue getReviewerProprietaryComments();
  String getReviewerProprietaryCommentsValue();
  void setReviewerProprietaryComments(StringValue reviewerProprietaryComments);
  void setReviewerProprietaryCommentsValue(String reviewerProprietaryComments);

  StringValue getReviewerStudyCallCriteria();
  String getReviewerStudyCallCriteriaValue();
  void setReviewerStudyCallCriteria(StringValue reviewerStudyCallCriteria);
  void setReviewerStudyCallCriteriaValue(String reviewerStudyCallCriteria);

  StringValue getReviewerStudyComments();
  String getReviewerStudyCommentsValue();
  void setReviewerStudyComments(StringValue reviewerStudyComments);
  void setReviewerStudyCommentsValue(String reviewerStudyComments);

  StringValue getStudyCallComments();
  String getStudyCallCommentsValue();
  void setStudyCallComments(StringValue studyCallComments);
  void setStudyCallCommentsValue(String studyCallComments);

  StudyCalls getStudyCalls();
  void setStudyCalls(StudyCalls studyCalls);

  StringValue getStudyComments();
  String getStudyCommentsValue();
  void setStudyComments(StringValue studyComments);
  void setStudyCommentsValue(String studyComments);

  StringValue getStudyType();
  String getStudyTypeValue();
  void setStudyType(StringValue studyType);
  void setStudyTypeValue(String studyType);

  ToxmlObjectList<? extends StudyTest> getTests();
}

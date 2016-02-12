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

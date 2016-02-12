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

import com.leadscope.stucco.model.TestCalls;
import com.leadscope.stucco.model.TestResultFindings;

public interface TestResults extends CompositeToxmlObject {
  StringValue getTestCallCriteria();
  String getTestCallCriteriaValue();
  void setTestCallCriteria(StringValue testCallCriteria);
  void setTestCallCriteriaValue(String testCallCriteria);

  TestCalls getTestCalls();
  void setTestCalls(TestCalls testCalls);

  StringValue getTestResultComments();
  String getTestResultCommentsValue();
  void setTestResultComments(StringValue testResultComments);
  void setTestResultCommentsValue(String testResultComments);

  TestResultFindings getTestResultFindings();
  void setTestResultFindings(TestResultFindings testResultFindings);

  StringValue getTestSubjectObservations();
  String getTestSubjectObservationsValue();
  void setTestSubjectObservations(StringValue testSubjectObservations);
  void setTestSubjectObservationsValue(String testSubjectObservations);

  StringValue getToxicityMeasure();
  String getToxicityMeasureValue();
  void setToxicityMeasure(StringValue toxicityMeasure);
  void setToxicityMeasureValue(String toxicityMeasure);
}

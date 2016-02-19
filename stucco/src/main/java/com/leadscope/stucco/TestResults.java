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

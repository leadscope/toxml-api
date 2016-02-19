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

public interface PhysicalCharacteristics extends CompositeToxmlObject {
  StringValue getColor();
  String getColorValue();
  void setColor(StringValue color);
  void setColorValue(String color);

  StringValue getColorIndex();
  String getColorIndexValue();
  void setColorIndex(StringValue colorIndex);
  void setColorIndexValue(String colorIndex);

  StringValue getComments();
  String getCommentsValue();
  void setComments(StringValue comments);
  void setCommentsValue(String comments);

  StringValue getGrade();
  String getGradeValue();
  void setGrade(StringValue grade);
  void setGradeValue(String grade);

  StringValue getPercentActive();
  String getPercentActiveValue();
  void setPercentActive(StringValue percentActive);
  void setPercentActiveValue(String percentActive);

  StringValue getPercentPurity();
  String getPercentPurityValue();
  void setPercentPurity(StringValue percentPurity);
  void setPercentPurityValue(String percentPurity);

  StringValue getPhysicalState();
  String getPhysicalStateValue();
  void setPhysicalState(StringValue physicalState);
  void setPhysicalStateValue(String physicalState);

  StringValue getStorageCondition();
  String getStorageConditionValue();
  void setStorageCondition(StringValue storageCondition);
  void setStorageConditionValue(String storageCondition);

  StringValue getTestForm();
  String getTestFormValue();
  void setTestForm(StringValue testForm);
  void setTestFormValue(String testForm);

  StringValue getTestMatrix();
  String getTestMatrixValue();
  void setTestMatrix(StringValue testMatrix);
  void setTestMatrixValue(String testMatrix);
}

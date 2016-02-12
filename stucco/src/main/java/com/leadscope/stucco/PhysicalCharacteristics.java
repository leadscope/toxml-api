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

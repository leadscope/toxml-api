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

public interface NegativeTestControl extends CompositeToxmlObject {
  StringValue getComments();
  String getCommentsValue();
  void setComments(StringValue comments);
  void setCommentsValue(String comments);

  StringValue getControlCompoundName();
  String getControlCompoundNameValue();
  void setControlCompoundName(StringValue controlCompoundName);
  void setControlCompoundNameValue(String controlCompoundName);

  StudyTreatment getTreatmentGroup();
}

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

import com.leadscope.stucco.model.TargetSites;

public interface EndPoint extends CompositeToxmlObject {
  StringValue getDescription();
  String getDescriptionValue();
  void setDescription(StringValue description);
  void setDescriptionValue(String description);

  StringValue getEndPointComment();
  String getEndPointCommentValue();
  void setEndPointComment(StringValue endPointComment);
  void setEndPointCommentValue(String endPointComment);

  StringValue getTargetSiteComment();
  String getTargetSiteCommentValue();
  void setTargetSiteComment(StringValue targetSiteComment);
  void setTargetSiteCommentValue(String targetSiteComment);

  TargetSites getTargetSites();
  void setTargetSites(TargetSites targetSites);

  StringValue getType();
  String getTypeValue();
  void setType(StringValue type);
  void setTypeValue(String type);

  Quantity getValue();
  void setValue(Quantity value);
}

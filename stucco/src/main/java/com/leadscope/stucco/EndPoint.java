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

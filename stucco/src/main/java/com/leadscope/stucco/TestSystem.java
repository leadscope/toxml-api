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

public interface TestSystem extends CompositeToxmlObject {
  StringValue getSpecies();
  String getSpeciesValue();
  void setSpecies(StringValue species);
  void setSpeciesValue(String species);

  StringValue getStrain();
  String getStrainValue();
  void setStrain(StringValue strain);
  void setStrainValue(String strain);

  StringValue getStrainCharacteristics();
  String getStrainCharacteristicsValue();
  void setStrainCharacteristics(StringValue strainCharacteristics);
  void setStrainCharacteristicsValue(String strainCharacteristics);

  StringValue getSupplier();
  String getSupplierValue();
  void setSupplier(StringValue supplier);
  void setSupplierValue(String supplier);

  StringValue getTargetCell();
  String getTargetCellValue();
  void setTargetCell(StringValue targetCell);
  void setTargetCellValue(String targetCell);

  StringValue getTestSystemComments();
  String getTestSystemCommentsValue();
  void setTestSystemComments(StringValue testSystemComments);
  void setTestSystemCommentsValue(String testSystemComments);
}

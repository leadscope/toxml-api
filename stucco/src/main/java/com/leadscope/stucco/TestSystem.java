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

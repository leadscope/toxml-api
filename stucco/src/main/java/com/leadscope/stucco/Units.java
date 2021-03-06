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

import java.util.ArrayList;
import java.util.List;

import com.leadscope.stucco.util.Displayable;
import com.leadscope.stucco.util.StringUtil;

/**
 * The units for a Quantity. The known units can be found in the KnownUnits singleton.
 */
public class Units extends AbstractPrimitiveToxmlObject implements Displayable, HashableToxmlObject {
  private List<UnitsTerm> terms = new ArrayList<UnitsTerm>();
  
  public Units() { }
  
  /**
   * Constructs the units from a / delimited string of units. Units not found in KnownUnits
   * will be treated as unknown terms that cannot be normalized.
   * @param value the string value of units
   */
  public Units(String value) {
    setValue(value);
  }
    
  public Units(List<UnitsTerm> terms) {
    this.terms = new ArrayList<UnitsTerm>(terms);
  }
  
  public <V> V accept(ToxmlVisitor<V> visitor) throws Exception {
    return visitor.visit(this);
  }
    
  /**
   * This method should only be used during deserialization
   */
  public void setValue(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Should not have null value - use null instead of " + getClass().getName());
    }
    for (String termString : value.split("/")) {
      termString = termString.trim();
      if (termString.length() == 0) {
        throw new IllegalArgumentException("Cannot have a blank unit term: [" + termString + "]");
      }
      UnitsTerm term = KnownMetrics.getInstance().getTerm(termString);
      if (term == null) {
        term = new UnitsTerm(termString);
      }
      terms.add(term);
    }
  }
    
  public List<UnitsTerm> getTerms() {
    return new ArrayList<UnitsTerm>(terms);
  }
  
  public String toString() {
    return StringUtil.join(terms, "/");
  }
  
  public String toDisplayableString() {
    return toString();
  }
  
  public boolean isUnitless() {
    return terms.size() == 1 && terms.get(0).equals(KnownMetrics.getInstance().getUnitlessTerm());
  }
  
  /**
   * Converts the given value in the given units to be in this
   * objects units.
   * @param value the value to convert
   * @param units the units that the given value is in
   * @return the equivalent value in the units represented by this object
   * @exception IncompatibleUnitsException if the units are not compatible
   * with these units
   */
  public float convertTo(float value, Units other) throws IncompatibleUnitsException {
    double doubleValue = (double)value;

    if (terms.size() != other.terms.size()) {
      throw new IllegalArgumentException("Incompatible units: " + toString() + " and " + other.toString());
    }

    for (int i = 0; i < terms.size(); i++) {
      UnitsTerm myTerm = (UnitsTerm)terms.get(i);
      UnitsTerm otherTerm = (UnitsTerm)other.terms.get(i);
      double ratio = myTerm.convertTo(1d, otherTerm);
      if (i == 0) {
        doubleValue *= ratio;
      }
      else {
        doubleValue /= ratio;
      }
    }

    return (float)doubleValue;
  }

  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (!getClass().equals(other.getClass())) {
      return false;
    }
    Units otherUnits = (Units)other;
    if (terms.size() > otherUnits.terms.size()) {
      return false;
    }
    for (int i = 0; i < terms.size(); i++) {
      if (!terms.get(i).equals(otherUnits.terms.get(i))) {
        return false;
      }
    }
    return true;
  }
  
  public int hashCode() {
    int prime = 31;
    int result = 17;
    for (UnitsTerm term : terms) {
      result = prime * result + term.hashCode();
    }
    return result;
  }
}

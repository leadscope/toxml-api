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
package com.leadscope.stucco.builder;

import org.antlr.stringtemplate.StringTemplate;

import java.util.*;
import java.util.stream.Collectors;

public class CompositeClass extends ToxmlClass {
  private List<CompositeMember> members = new ArrayList<>();

  public CompositeClass copy(String newName) {
    try {
      CompositeClass newClass = getClass().newInstance();
      newClass.setName(newName);
      for (CompositeMember member : members) {
        newClass.members.add(member.copy());
      }
      return newClass;
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  public List<CompositeMember> getMembersWithVocabulary() {
    List<CompositeMember> membersWithVocab = new ArrayList<CompositeMember>();
    for (CompositeMember member : members) {
      if (member.isHasVocabulary()) {
        membersWithVocab.add(member);
      }
    }
    return membersWithVocab;
  }
  
  public boolean isHasMembersWithVocabulary() {
    return getMembersWithVocabulary().size() > 0;
  }
  
  public Set<String> getChildTypeNames() {
    Set<String> typeNames = new HashSet<String>();
    for (CompositeMember member : members) {
      typeNames.add(member.getType().getName());
    }
    return typeNames;
  }
  
  public List<String> getMemberTags() {
    List<String> tags = new ArrayList<String>();
    for (CompositeMember member : members) {
      tags.add(member.getTag());
    }
    return tags;
  }
  
  public CompositeMember getMember(String tag) {
    for (CompositeMember member : members) {
      if (member.getTag().equals(tag)) {
        return member;
      }
    }
    return null;
  }
  
  public boolean isUsingModelPackage() {
    if (getPackageName() == null) {
      return true;
    }
    for (CompositeMember member : members) {
      ToxmlClass memberType = member.getType(); 
      if (memberType.getPackageName() == null && !(memberType instanceof PrimitiveClass)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean isUsingUtilPackage() {
    return true;    
  }
  
  public CompositeMember addMember(String tag, ToxmlClass toxmlClass) {
    if (toxmlClass.getName() == null) {
      throw new RuntimeException("Got null name for: " + tag + " - " + toxmlClass.getClass().getSimpleName());
    }
    if (getMember(tag) != null) {
      throw new RuntimeException("Already added member for tag: " + tag);
    }
    CompositeMember member = new CompositeMember(tag, toxmlClass);
    members.add(member);
    Collections.sort(members, memberComparator);
    return member;
  }

  public ToxmlClass getType(String tag) {
    return getMember(tag).getType();
  }
  
  public void addPreferredUnit(String tag, String units) {
    CompositeMember member = getMember(tag);
    if (member == null) {
      throw new RuntimeException("No such member tag: " + tag);
    }
    member.addPreferredUnits(units);
  }
  
  public List<String> getPreferredUnits(String tag) {
    CompositeMember member = getMember(tag);
    if (member == null) {
      throw new RuntimeException("No such member tag: " + tag);
    }
    return member.getPreferredUnits();
  }
  
  public void setDescription(String tag, String description) {
    CompositeMember member = getMember(tag);
    if (member == null) {
      throw new RuntimeException("No such member tag: " + tag);
    }
    member.setDescription(description);
  }
  
  public String getDescription(String tag) {
    CompositeMember member = getMember(tag);
    if (member == null) {
      throw new RuntimeException("No such member tag: " + tag);
    }
    return member.getDescription();
  }

  protected List<String> getInterfaceNames() {
    List<String> commonSuffixes = Arrays.asList(
            "TestResults", "TestSystem",
            "TestCondition", "NegativeTestControl", "PositiveTestControl",
            "PhysicalCharacteristics", "SolventVehicleSubstanceQuantity",
            "EndPoint"
    );

    for (String suffix : commonSuffixes) {
      if (getName().equals(getStudyTypeName() + suffix)) {
        return Arrays.asList(suffix);
      }
    }
    if (getName().equals(getStudyTypeName() + "Treatment")) {
      return Arrays.asList("StudyTreatment");
    }
    if (getName().equals(getStudyTypeName() + "GenericResultFindings")) {
      return Arrays.asList("TreatmentResultFindings");
    }
    if (getName().equals(getStudyTypeName() + "Background")) {
      return Arrays.asList("StudyBackground");
    }
    if (getName().equals(getStudyTypeName() + "Results")) {
      return Arrays.asList("TreatmentResults");
    }
    return new ArrayList<>(0);
  }

  public void fillInTemplate(StringTemplate st) {
    st.setAttribute("members", members);
    if (getInterfaceNames().size() > 0) {
      String interfaceList = getInterfaceNames().stream().collect(Collectors.joining(", "));
      st.setAttribute("implementsString", "implements " + interfaceList + " ");
    }
    else {
      st.setAttribute("implementsString", "");
    }

  }
  
  public String toString(String tag, int indent) {
    StringBuilder sb = new StringBuilder();
    sb.append(super.toString(tag, indent));
    sb.append("\n");
    for (String memberTag : getMemberTags()) {
      ToxmlClass childClass = getType(memberTag);
      sb.append(childClass.toString(memberTag, indent+1));
      sb.append("\n");
    }
    
    return sb.toString();
  }
  
  public boolean isEquivalent(ToxmlClass other) {
    if (!getClass().equals(other.getClass())) {
      return false;
    }
    
    CompositeClass otherComposite = (CompositeClass)other;
    if (members.size() != otherComposite.members.size()) {
      return false;
    }
    
    for (int i = 0; i < members.size(); i++) {
      CompositeMember myMember = members.get(i);
      CompositeMember otherMember = otherComposite.members.get(i);
      if (!myMember.isEquivalent(otherMember)) {
        return false;
      }
    }
    
    return true;
  }
  
  private static TagComparator memberComparator = new TagComparator();
  private static class TagComparator implements Comparator<CompositeMember> {
    public int compare(CompositeMember o1, CompositeMember o2) {
      return o1.getTag().compareTo(o2.getTag());
    }    
  }

  public void changeChildType(String oldTypeName, ToxmlClass newClass) {
    for (CompositeMember member : members) {
      if (member.getType().getName().equals(oldTypeName)) {
        member.setType(newClass);
      }
    }
  }
}

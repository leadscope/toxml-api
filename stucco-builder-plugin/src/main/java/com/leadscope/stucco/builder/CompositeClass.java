/**
 * Stucco ToxML API - a Java interface for parsing and creating ToxML documents
 * Copyright (C) 2011 Scott Miller - Leadscope, Inc.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.leadscope.stucco.builder;

import java.util.*;

import org.antlr.stringtemplate.StringTemplate;

public class CompositeClass extends ToxmlClass {
  private List<CompositeMember> members = new ArrayList<CompositeMember>();

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
    
  public void fillInTemplate(StringTemplate st) {
    st.setAttribute("members", members);
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

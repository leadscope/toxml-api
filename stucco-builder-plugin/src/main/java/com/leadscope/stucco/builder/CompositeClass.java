/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
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
  
  public boolean equivalent(ToxmlClass other) {
    if (!getClass().equals(other.getClass())) {
      return false;
    }
    
    CompositeClass otherComposite = (CompositeClass)other;
    
    List<String> myTags = getMemberTags();
    List<String> otherTags = otherComposite.getMemberTags();    
    if (myTags.size() != otherTags.size()) { 
      return false;
    }
    for (int i = 0; i < myTags.size(); i++) {
      String myTag = myTags.get(i);
      String otherTag = otherTags.get(i);
      if (!myTag.equals(otherTag)) {
        return false;
      }
      ToxmlClass myClass = getType(myTag);
      ToxmlClass otherClass = otherComposite.getType(otherTag);
      if (!myClass.equivalent(otherClass)) {
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

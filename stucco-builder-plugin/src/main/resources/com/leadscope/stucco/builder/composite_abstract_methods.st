private List<String> childTags = Arrays.asList( $trunc(members): { member |
  "$member.tag$", }$
  "$last(members).tag$"
);
public List<String> getChildTags() {
  return childTags;
}

public ToxmlObject getChild(String tag) { $first(members): { member |
  if (tag.equals("$member.tag$")) {
    return $member.camelTag$;
  } }$ $rest(members): { member |
  else if (tag.equals("$member.tag$")) {
    return $member.camelTag$;
  } }$
  else {
    throw new IllegalArgumentException("Unknown tag: " + tag);
  }
}

public void setChild(String tag, ToxmlObject value) { $first(members): { member |
  if (tag.equals("$member.tag$")) {
    set$member.javaSafeTag$(($member.type.name$)value);
  } }$ $rest(members): { member |
  else if (tag.equals("$member.tag$")) {
    set$member.javaSafeTag$(($member.type.name$)value);
  } }$
  else {
    throw new IllegalArgumentException("Unknown tag: " + tag);
  }
}

public Class<? extends ToxmlObject> getChildClass(String tag) { $first(members): { member |
  if (tag.equals("$member.tag$")) {
    return $member.type.name$.class;
  } }$ $rest(members): { member |
  else if (tag.equals("$member.tag$")) {
    return $member.type.name$.class;
  } }$
  else {
    throw new IllegalArgumentException("Unknown tag: " + tag);
  }
}

public List<String> getVocabulary(String tag) {
$if (class.hasMembersWithVocabulary)$
  if (tag.equals("$first(class.membersWithVocabulary).tag$")) {
    return get$first(class.membersWithVocabulary).javaSafeTag$Vocabulary(); $rest(class.membersWithVocabulary): { member |
  \}
  else if (tag.equals("$member.tag$")) \{
    return get$member.javaSafeTag$Vocabulary(); }$
  }
  else {
    return new ArrayList<String>(0);
  }
$else$
  return new ArrayList<String>(0);
$endif$

}
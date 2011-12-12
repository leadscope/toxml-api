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
    this.$member.camelTag$ = ($member.type.name$)value;
  } }$ $rest(members): { member |
  else if (tag.equals("$member.tag$")) {
    this.$member.camelTag$ = ($member.type.name$)value;
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

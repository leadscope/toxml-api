/**
 * @return the $primitive$ value for - $member.commentString$
 */
public $primitive$ get$member.javaSafeTag$Value() {
  if ($member.camelTag$ == null) {
    return null;
  }
  else {
    return $member.camelTag$.getValue();
  }
}

/**
 * @return the toxml object value for - $member.commentString$
 */
public $member.type.name$ get$member.javaSafeTag$() {
  return $member.camelTag$;
}

/**
 * Creates a new toxml object and assigns this object as its parent - any previous value
 * will have its parent set to null
 * @param $member.camelTag$ the $primitive$ value for - $member.commentString$
 */
public void set$member.javaSafeTag$Value($primitive$ $member.camelTag$) {
  if ($member.camelTag$ == null) {
    set$member.javaSafeTag$(null);
  }
  else {
    set$member.javaSafeTag$(new $member.type.name$($member.camelTag$));
  }
}

/**
 * Sets the value and assigns this object as its parent - any previous value will have its
 * parent set to null
 * @param $member.camelTag$ the toxml object value for - $member.commentString$
 */
public void set$member.javaSafeTag$($member.type.name$ $member.camelTag$) {
  if ($member.camelTag$ != null) {
    if ($member.camelTag$.getParent() != null) {
      throw new IllegalArgumentException(
          "$member.camelTag$ is already assigned to another object");
    }
    $member.camelTag$.setParent(this);
  }
  $member.type.name$ oldValue = this.$member.camelTag$;
  if (oldValue != null) {
    oldValue.setParent(null);
  }
  this.$member.camelTag$ = $member.camelTag$;
  updatedValue("$member.tag$", oldValue, $member.camelTag$);
}

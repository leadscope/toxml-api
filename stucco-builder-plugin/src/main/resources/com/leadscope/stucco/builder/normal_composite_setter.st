/**
 * Sets the value and assigns this object as its parent - any previous object will
 * have its parent set to null
 * @param $member.camelTag$ $member.commentString$
 * @IllegalArgumentException if $member.camelTag$ has a non-null parent
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

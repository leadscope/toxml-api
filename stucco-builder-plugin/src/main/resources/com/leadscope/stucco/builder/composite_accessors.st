$members:{ member |
$if (member.string)$
$com/leadscope/stucco/builder/composite_primitive_accessors(primitive="String")$

$elseif (member.cdata)$
$com/leadscope/stucco/builder/composite_primitive_accessors(primitive="String")$

$elseif (member.float)$
$com/leadscope/stucco/builder/composite_primitive_accessors(primitive="Float")$

$elseif (member.boolean)$
$com/leadscope/stucco/builder/composite_primitive_accessors(primitive="Boolean")$

$elseif (member.integer)$
$com/leadscope/stucco/builder/composite_primitive_accessors(primitive="Integer")$

$elseif (member.collectionOrComposite)$
/**
 * @return $member.commentString$ - never null, returns empty container if missing
 */
public $member.type.name$ get$member.javaSafeTag$() {
  if ($member.camelTag$ == null) {
    $member.camelTag$ = new $member.type.name$();
    $member.camelTag$.setParent(this);
  }
  return $member.camelTag$;
}

$com/leadscope/stucco/builder/normal_composite_setter()$

$else$
/**
 * @return $member.commentString$
 */
public $member.type.name$ get$member.javaSafeTag$() {
  return $member.camelTag$;
}

$com/leadscope/stucco/builder/normal_composite_setter()$

$endif$ 
$if (member.hasVocabulary)$
/**
 * Gets the vocabulary values for - $member.commentString$ 
 * @return the list of recommended values given the current state of the object
 */ 
public List<String> get$member.javaSafeTag$Vocabulary() \{ 
$member.dependencyVocabularies: { vocab | 
  if (valueByPathEquals(new ToxmlPath("$first(vocab.dependencies).relativePathToParent$"),
                        "$first(vocab.dependencies).value$") $rest(vocab.dependencies): { dep |
      && valueByPathEquals(new ToxmlPath("$dep.relativePathToParent$"),
                           "$dep.value$") }$) \{ 
    return Arrays.asList( $trunc(vocab.values): { value |
        "$value$", }$
        "$last(vocab.values)$"          
    ); 
  \} }$
$if (member.nonDependencyVocabulary)$
  return Arrays.asList( $trunc(member.nonDependencyVocabulary.values): { value |
      "$value$", }$
      "$last(member.nonDependencyVocabulary.values)$"          
  );
$else$
  return new ArrayList<String>(0);
$endif$    
\}  

$endif$ }$
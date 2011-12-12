$com/leadscope/stucco/builder/header()$
$members:{ member |
import com.leadscope.stucco.model.$member.type.packageName$.$member.type.name$; }$

/**
 * $commentString$
 */
public class $className$ extends AbstractCompositeToxmlObject { $members:{ member |
  private $member.type.name$ $member.camelTag$; }$

  $com/leadscope/stucco/builder/composite_accessors()$
  $com/leadscope/stucco/builder/composite_abstract_methods()$
}


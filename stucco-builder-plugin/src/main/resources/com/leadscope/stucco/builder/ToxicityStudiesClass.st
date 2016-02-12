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

  public List<Study> getAllStudies() {
    List<Study> studies = new ArrayList<>();
    for (ToxmlObject child : getChildren()) {
      AbstractToxmlObjectList<? extends Study> studyList = (AbstractToxmlObjectList<? extends Study>)child;
      studyList.stream()
              .forEachOrdered(study -> studies.add(study));
    }
    return studies;
  }
}


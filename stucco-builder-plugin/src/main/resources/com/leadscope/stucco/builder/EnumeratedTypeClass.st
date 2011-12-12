$com/leadscope/stucco/builder/header()$

/**
 * $commentString$
 */
public class $className$ extends AbstractToxmlEnumeratedType {
  private static List<String> allValues = Arrays.asList($trunc(values): {value|
    "$value$", }$
    "$last(values)$"
  );

  public List<String> getAllValues() {
    return allValues;
  }
}


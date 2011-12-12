$com/leadscope/stucco/builder/header()$

/**
 * A utility class for integrating the generated source code with the abstract
 * framework
 */
public class GeneratedModel {
  public static List<Metric> getKnownMetrics() {
    List<Metric> metrics = new ArrayList<Metric>(); $metrics:{ metric |
    \{
       Metric metric = new Metric("$metric.name$");
       metrics.add(metric); $metric.units:{ unit |
       metric.addTerm("$unit.name$", $unit.fractionOfBase$, $unit.magnitude$l); }$
    \}
}$
    return metrics;
  }

  public static Class<$compoundClass$> getCompoundClass() {
    return $compoundClass$.class;
  }
}


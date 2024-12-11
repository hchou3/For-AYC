import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Correlations {
  public static <T> double standardDeviation(List<T> elements, Function<T, Double> property) {
    List<Double> dList = elements.stream().map(property).collect(Collectors.toList());
    int count = dList.size();
    BinaryOperator<Double> sumF = (x, y) -> x + y;
    double sum = dList.stream().reduce(0.0, sumF);
    double avg = sum / count;
    List<Double> listPow = dList.stream().map(i -> Math.pow(i - avg, 2.0)).collect(Collectors.toList());
    double convert = listPow.stream().reduce(0.0, sumF);
    double dev = Math.sqrt(convert / (count - 1));
    return dev;
  }
    
  public static <T> double correlation(List<T> elements, Function<T, Double> property1, Function<T, Double> property2) {
    List<Double> dList1 = elements.stream().map(property1).collect(Collectors.toList());
    List<Double> dList2 = elements.stream().map(property2).collect(Collectors.toList());
    int count1 = dList1.size();
    int count2 = dList2.size();
    BinaryOperator<Double> sumF = (x, y) -> x + y;
    double sumX = dList1.stream().reduce(0.0, sumF);
    double sumY = dList2.stream().reduce(0.0, sumF);
    double avgX = sumX / count1;
    double avgY = sumY / count2;
    List<Double> lX = dList1.stream().map(i -> i - avgX).collect(Collectors.toList());
    List<Double> lY = dList2.stream().map(i -> i - avgY).collect(Collectors.toList());
    List<Double> m = IntStream.range(0, lX.size()).mapToDouble(i -> lX.get(i) * lY.get(i)).boxed().collect(Collectors.toList());
    double mSum = m.stream().reduce(0.0, sumF);
    double devX = standardDeviation(elements, property1);
    double devY = standardDeviation(elements, property2);
    double corr = (mSum) / ((devX * devY) * (count1-1));
    return corr;
  }

  public static final double EPSILON = 1e-10; 
  public static boolean areDoublesEqual(double a, double b) {
    return Math.abs(a - b) < EPSILON;
  }

  public static <T> List<String> topKCorrelations(List<T> elements, Map<String, Function<T, Double>> allProperties, String testPropertyName, int k) {
    Map<String, Double> clist = allProperties.entrySet().stream().collect(Collectors.toMap(
        e -> e.getKey(), e -> correlation(elements, allProperties.get(testPropertyName), e.getValue())
    ));
    List<Function<T, Double>> m = new ArrayList<>(allProperties.values());
    List<Double> corlist = m.stream().map(i -> correlation(elements, allProperties.get(testPropertyName), i)).collect(Collectors.toList());
    corlist.sort((a, b) -> -Double.compare(a, b));

    Function<Double, String> sortCorr = corr -> {
        for (String key : clist.keySet()) {
            if (areDoublesEqual(clist.get(key), corr)) {
                return key;
            }
        }
        return "";
    };
    List<String> spropList = corlist.stream().map(sortCorr).collect(Collectors.toList());
    return spropList.subList(0, k);
    }

}



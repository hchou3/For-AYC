import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


public class RecommendationApp {
    public static final int numberOfRecommendations = 6;

    public static Function<Customer, Double> getStat(String item) {
        return c -> c.getPurchases().get(item) != null ? c.getPurchases().get(item).doubleValue() : 0.0;
    }

    public static List<String> recommendations(String filename, String viewingItem) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String header = br.readLine();
        String[] itemNames = header.split(",");
        List<Customer> customers = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            Map<String, Integer> items = new HashMap<>();
            for (int i = 0; i < itemNames.length; i++) {
                items.put(itemNames[i], Integer.valueOf(values[i]));
            }
            customers.add(new Customer(items));
        }

        Map<String, Function<Customer, Double>> allProperties = new HashMap<>();
        for (String itemName : itemNames) {
            allProperties.put(itemName, getStat(itemName));
        }


        return Correlations.topKCorrelations(customers, allProperties, viewingItem, numberOfRecommendations);
    }

    public static int getNumberOfRecommendations() {
        return numberOfRecommendations;
    }
}


import java.util.Map;

public class Customer {
  private final Map<String, Integer> purchases;

  public Customer(Map<String, Integer> purchases) {
    this.purchases = purchases;
  }

  public Map<String, Integer> getPurchases() {
    return purchases;
  }
}

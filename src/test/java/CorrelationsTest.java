import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class CorrelationsTest {

    @Test
    public void testStandardDeviation() {
        // Test data
        List<Double> data = Arrays.asList(2.0, 4.0, 4.0, 4.0, 5.0, 5.0, 7.0, 9.0);

        // Use the standardDeviation method with property as identity function
        double result = Correlations.standardDeviation(data, x -> x);

        // Expected standard deviation (calculated separately)
        double expected = 2.0;

        // Verify the result with a small tolerance to handle floating-point arithmetic
        assertEquals(expected, result, 0.0001, "Standard deviation calculation is incorrect.");
    }

    @Test
    public void testCorrelation() {
        // Test data
        List<DataPoint> dataPoints = Arrays.asList(
            new DataPoint(1, 2),
            new DataPoint(2, 3),
            new DataPoint(3, 4),
            new DataPoint(4, 5),
            new DataPoint(5, 6)
        );

        // Use the correlation method with property1 and property2 as getters for x and y values
        double result = Correlations.correlation(dataPoints, DataPoint::getX, DataPoint::getY);

        // Expected correlation (in this case, we expect a perfect correlation of 1)
        double expected = 1.0;

        // Verify the result with a small tolerance to handle floating-point arithmetic
        assertEquals(expected, result, 0.0001, "Correlation calculation is incorrect.");
    }

    // Helper class to hold data points for correlation testing
    private static class DataPoint {
        private final double x;
        private final double y;

        public DataPoint(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}

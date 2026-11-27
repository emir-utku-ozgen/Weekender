import java.util.*;

public class PriceEstimator {
    private static final double PRICE_PER_KM = 0.15;

    public static double estimateConnectionCost(Connection c, Map<Integer, StopLocation> locations) {
        StopLocation from = locations.get(c.fromStop());
        StopLocation to = locations.get(c.toStop());
        double km = Distance.haversineKm(from.lat(), from.lon(), to.lat(), to.lon());
        return km * PRICE_PER_KM;
    }

    public static double estimateRouteCost(List<Connection> route, Map<Integer, StopLocation> locations) {
        double total = 0;
        for (Connection c : route) {
            total += estimateConnectionCost(c, locations);
        }
        return total;
    }
}

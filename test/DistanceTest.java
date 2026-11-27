import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DistanceTest {

    @Test
    void sameCoordinatesHaveZeroDistance() {
        double distance = Distance.haversineKm(50.0, 10.0, 50.0, 10.0);
        assertEquals(0.0, distance, 0.0001);
    }

    @Test
    void berlinToMunichMatchesKnownApproximateDistance() {
        // Berlin Hbf ve Münih Hbf arası kuş uçuşu mesafe ~504 km
        double distance = Distance.haversineKm(52.5200, 13.4050, 48.1374, 11.5755);
        assertEquals(504.0, distance, 5.0);
    }

    @Test
    void distanceIsSymmetric() {
        double forward = Distance.haversineKm(52.5200, 13.4050, 48.1374, 11.5755);
        double backward = Distance.haversineKm(48.1374, 11.5755, 52.5200, 13.4050);
        assertEquals(forward, backward, 0.0001);
    }
}

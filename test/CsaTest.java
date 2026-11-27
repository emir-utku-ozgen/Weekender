import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsaTest {

    @Test
    void earliestArrivalFollowsTwoHopRoute() {
        // 0 --(100->200)--> 1 --(250->300)--> 2 ; stop 3 kimseye bağlı değil
        List<Connection> connections = List.of(
                new Connection(0, 1, 100, 200, "trip1"),
                new Connection(1, 2, 250, 300, "trip2")
        );

        CsaResult result = Csa.run(connections, 4, 0, 100);

        assertEquals(100, result.earliestArrival()[0]);
        assertEquals(200, result.earliestArrival()[1]);
        assertEquals(300, result.earliestArrival()[2]);
        assertEquals(Integer.MAX_VALUE, result.earliestArrival()[3]);
    }

    @Test
    void missedConnectionLeavesStopUnreachable() {
        // Kalkış 100'dan sonra başlıyorsak (dep=50), bu bağlantıya binemeyiz
        List<Connection> connections = List.of(
                new Connection(0, 1, 50, 150, "trip1")
        );

        CsaResult result = Csa.run(connections, 2, 0, 100);

        assertEquals(Integer.MAX_VALUE, result.earliestArrival()[1]);
    }

    @Test
    void buildRouteReturnsConnectionsInTravelOrder() {
        Connection first = new Connection(0, 1, 100, 200, "trip1");
        Connection second = new Connection(1, 2, 250, 300, "trip2");
        List<Connection> connections = List.of(first, second);

        CsaResult result = Csa.run(connections, 3, 0, 100);
        List<Connection> route = Csa.buildRoute(result, 2);

        assertEquals(2, route.size());
        assertEquals(first, route.get(0));
        assertEquals(second, route.get(1));
    }
}

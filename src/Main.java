import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Weekender başlıyor");

        Map<String, Integer> stopIndex = new HashMap<>();
        List<Connection> connections = GtfsReader.readConnections("data/de-fv/stop_times.txt", stopIndex);
        connections.sort(Comparator.comparingInt(Connection::depTime));
        for (int i = 0; i < 5; i++) {
            Connection c = connections.get(i);
            System.out.println(
                    GtfsTime.formatTime(c.depTime()) + " → " +
                            GtfsTime.formatTime(c.arrTime()) +
                            "  trip=" + c.tripId() +
                            "  " + c.fromStop() + " → " + c.toStop()
            );
        }

        int source = 0;
        int startTime = connections.get(0).depTime();
        CsaResult result = Csa.run(connections, stopIndex.size(), source, startTime);

        int reachable = 0;
        for (int t : result.earliestArrival()) {
            if (t != Integer.MAX_VALUE) reachable++;
        }
        System.out.println("Kaynak durak " + source + ", başlangıç " + GtfsTime.formatTime(startTime));
        System.out.println("Ulaşılabilen durak sayısı: " + reachable + " / " + stopIndex.size());
    }
}
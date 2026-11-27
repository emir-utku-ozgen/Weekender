import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Weekender başlıyor");

        Map<String, Integer> stopIndex = new HashMap<>();
        List<Connection> connections = GtfsReader.readConnections("data/de-fv/stop_times.txt", stopIndex);
        connections.sort(Comparator.comparingInt(Connection::depTime));

        int source = 0;
        int target = 100;
        int startTime = connections.get(0).depTime();

        System.out.println("Kaynak durak " + source + ", hedef durak " + target +
                ", başlangıç " + GtfsTime.formatTime(startTime));

        long start = System.nanoTime();
        Journey journey = JourneyPlanner.plan(connections, stopIndex.size(), source, target, startTime);
        long end = System.nanoTime();
        System.out.println("CSA süresi: " + (end - start) / 1_000_000.0 + " ms");

        RoutePrinter.print(journey);
    }
}

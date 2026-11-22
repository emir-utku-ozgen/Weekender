import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Weekender başlıyor");

        Map<String, Integer> stopIndex = new HashMap<>();
        List<Connection> connections = GtfsReader.readConnections("data/de-fv/stop_times.txt", stopIndex);

        System.out.println("Durak sayısı: " + stopIndex.size());
        System.out.println("Bağlantı sayısı: " + connections.size());
        System.out.println("İlk bağlantı: " + connections.get(0));
    }
}
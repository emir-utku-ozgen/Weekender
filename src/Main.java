import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Weekender başlıyor");

        Map<String, Integer> stopIndex = new HashMap<>();
        List<Connection> connections = GtfsReader.readConnections("data/de-fv/stop_times.txt", stopIndex);
        connections.sort(Comparator.comparingInt(Connection::depTime));
        Map<Integer, StopLocation> locations = GtfsReader.readStopLocations("data/de-fv/stops.txt", stopIndex);

        Map<String, String> tripServices = GtfsReader.readTripServices("data/de-fv/trips.txt");
        Map<String, Calendar> calendars = GtfsReader.readCalendars("data/de-fv/calendar.txt");
        Map<String, Map<LocalDate, Integer>> exceptions = GtfsReader.readCalendarExceptions("data/de-fv/calendar_dates.txt");
        TripCalendar tripCalendar = new TripCalendar(tripServices, calendars, exceptions);

        LocalDate rangeStart = LocalDate.of(2026, 11, 27);
        LocalDate rangeEnd = LocalDate.of(2026, 11, 29);
        List<Connection> timetable = tripCalendar.buildTimetable(connections, rangeStart, rangeEnd);

        int source = 0;
        int target = 100;
        int startTime = (int) (rangeStart.toEpochDay() * 86400L) + 14 * 3600;

        System.out.println(rangeStart + " - " + rangeEnd + " aralığı, kaynak durak " + source +
                ", hedef durak " + target + ", başlangıç " + rangeStart + " 14:00");

        long start = System.nanoTime();
        Journey journey = JourneyPlanner.plan(timetable, stopIndex.size(), source, target, startTime, locations);
        long end = System.nanoTime();
        System.out.println("CSA süresi: " + (end - start) / 1_000_000.0 + " ms");

        RoutePrinter.print(journey);
    }
}

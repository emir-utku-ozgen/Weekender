import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GtfsReaderCalendarTest {

    @Test
    void parsesTripToServiceMapping(@TempDir Path tempDir) throws IOException {
        Path tripsFile = tempDir.resolve("trips.txt");
        Files.writeString(tripsFile,
                "route_id,service_id,trip_id\n" +
                "1,31,13ge2\n" +
                "1,4s,cqaz\n"
        );

        Map<String, String> tripServices = GtfsReader.readTripServices(tripsFile.toString());

        assertEquals("31", tripServices.get("13ge2"));
        assertEquals("4s", tripServices.get("cqaz"));
    }

    @Test
    void parsesCalendarDaysAndDateRange(@TempDir Path tempDir) throws IOException {
        Path calendarFile = tempDir.resolve("calendar.txt");
        Files.writeString(calendarFile,
                "monday,tuesday,wednesday,thursday,friday,saturday,sunday,start_date,end_date,service_id\n" +
                "0,0,0,0,0,0,1,20261121,20261229,4s\n" +
                "1,1,1,1,1,0,0,20261101,20261130,31\n"
        );

        Map<String, Calendar> calendars = GtfsReader.readCalendars(calendarFile.toString());

        Calendar sundayOnly = calendars.get("4s");
        assertTrue(sundayOnly.sunday());
        assertFalse(sundayOnly.monday());
        assertEquals(LocalDate.of(2026, 11, 21), sundayOnly.startDate());
        assertEquals(LocalDate.of(2026, 12, 29), sundayOnly.endDate());

        Calendar weekdayOnly = calendars.get("31");
        assertTrue(weekdayOnly.friday());
        assertFalse(weekdayOnly.saturday());
    }
}

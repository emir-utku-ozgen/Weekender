import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class GtfsTimeTest {

    @Test
    void formatAbsoluteRoundTripsDateAndTimeOfDay() {
        LocalDate date = LocalDate.of(2026, 11, 27);
        int absoluteSeconds = (int) (date.toEpochDay() * 86400L) + 14 * 3600;

        assertEquals("2026-11-27 14:00", GtfsTime.formatAbsolute(absoluteSeconds));
    }

    @Test
    void formatAbsoluteRollsOverPastMidnightIntoNextCalendarDay() {
        LocalDate date = LocalDate.of(2026, 11, 27);
        // service day saati 25:30 -> aslında 28 Kasım 01:30
        int absoluteSeconds = (int) (date.toEpochDay() * 86400L) + 25 * 3600 + 30 * 60;

        assertEquals("2026-11-28 01:30", GtfsTime.formatAbsolute(absoluteSeconds));
    }
}

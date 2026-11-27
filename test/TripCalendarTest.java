import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TripCalendarTest {

    private final Calendar weekdayOnlyCalendar = new Calendar(
            true, true, true, true, true, false, false,
            LocalDate.of(2026, 11, 1),
            LocalDate.of(2026, 11, 30)
    );

    @Test
    void fallsBackToCalendarWhenNoException() {
        // 2026-11-25 bir Çarşamba, hiç istisna yok
        TripCalendar tripCalendar = new TripCalendar(
                Map.of("trip1", "serviceA"),
                Map.of("serviceA", weekdayOnlyCalendar),
                Map.of()
        );

        assertTrue(tripCalendar.isTripActiveOn("trip1", LocalDate.of(2026, 11, 25)));
    }

    @Test
    void addedExceptionOverridesInactiveCalendarDay() {
        // 2026-11-28 bir Cumartesi, normalde inaktif ama istisna ile eklenmiş (type=1)
        LocalDate saturday = LocalDate.of(2026, 11, 28);
        TripCalendar tripCalendar = new TripCalendar(
                Map.of("trip1", "serviceA"),
                Map.of("serviceA", weekdayOnlyCalendar),
                Map.of("serviceA", Map.of(saturday, 1))
        );

        assertTrue(tripCalendar.isTripActiveOn("trip1", saturday));
    }

    @Test
    void removedExceptionOverridesActiveCalendarDay() {
        // 2026-11-25 bir Çarşamba, normalde aktif ama istisna ile çıkarılmış (type=2)
        LocalDate wednesday = LocalDate.of(2026, 11, 25);
        TripCalendar tripCalendar = new TripCalendar(
                Map.of("trip1", "serviceA"),
                Map.of("serviceA", weekdayOnlyCalendar),
                Map.of("serviceA", Map.of(wednesday, 2))
        );

        assertFalse(tripCalendar.isTripActiveOn("trip1", wednesday));
    }

    @Test
    void unknownServiceIsInactive() {
        TripCalendar tripCalendar = new TripCalendar(
                Map.of("trip1", "unknownService"),
                Map.of("serviceA", weekdayOnlyCalendar),
                Map.of()
        );

        assertFalse(tripCalendar.isTripActiveOn("trip1", LocalDate.of(2026, 11, 25)));
    }
}

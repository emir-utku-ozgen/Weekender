import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarTest {

    private final Calendar weekdayOnlyCalendar = new Calendar(
            true, true, true, true, true, false, false,
            LocalDate.of(2026, 11, 1),
            LocalDate.of(2026, 11, 30)
    );

    @Test
    void activeOnMatchingWeekdayWithinRange() {
        // 2026-11-25 bir Çarşamba
        assertTrue(weekdayOnlyCalendar.isActiveOn(LocalDate.of(2026, 11, 25)));
    }

    @Test
    void inactiveOnWeekendEvenWithinRange() {
        // 2026-11-28 bir Cumartesi
        assertFalse(weekdayOnlyCalendar.isActiveOn(LocalDate.of(2026, 11, 28)));
    }

    @Test
    void inactiveBeforeStartDate() {
        assertFalse(weekdayOnlyCalendar.isActiveOn(LocalDate.of(2026, 10, 31)));
    }

    @Test
    void inactiveAfterEndDate() {
        assertFalse(weekdayOnlyCalendar.isActiveOn(LocalDate.of(2026, 12, 1)));
    }
}

import java.time.LocalDate;

public record Calendar(
        boolean monday, boolean tuesday, boolean wednesday, boolean thursday,
        boolean friday, boolean saturday, boolean sunday,
        LocalDate startDate, LocalDate endDate
) {
    public boolean isActiveOn(LocalDate date) {
        if (date.isBefore(startDate) || date.isAfter(endDate)) {
            return false;
        }
        return switch (date.getDayOfWeek()) {
            case MONDAY -> monday;
            case TUESDAY -> tuesday;
            case WEDNESDAY -> wednesday;
            case THURSDAY -> thursday;
            case FRIDAY -> friday;
            case SATURDAY -> saturday;
            case SUNDAY -> sunday;
        };
    }
}

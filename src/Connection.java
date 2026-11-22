public record Connection(
        int fromStop,
        int toStop,
        int depTime,
        int arrTime,
        String tripId
) {
}
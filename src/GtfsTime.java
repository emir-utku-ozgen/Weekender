import java.time.LocalDate;

public class GtfsTime {
    public static int parseTime(String s){
        String [] parts=s.split(":");
        int hour=Integer.parseInt(parts[0]);
        int minute=Integer.parseInt(parts[1]);
        int second=Integer.parseInt(parts[2]);
        int totalSeconds=hour*3600+minute*60+second;
        return totalSeconds;
    }
    public static String formatTime(int seconds){
        int hour=seconds/3600;
        int minutes=(seconds%3600)/60;
        return String.format("%02d:%02d", hour, minutes);
    }

    public static String formatAbsolute(int absoluteSeconds) {
        long day = Math.floorDiv(absoluteSeconds, 86400);
        int secondsOfDay = Math.floorMod(absoluteSeconds, 86400);
        LocalDate date = LocalDate.ofEpochDay(day);
        return date + " " + formatTime(secondsOfDay);
    }
}

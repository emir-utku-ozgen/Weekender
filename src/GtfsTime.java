public class GtfsTime {
    public static int parseTime(String s){
        String [] parts=s.split(":");
        int hour=Integer.parseInt(parts[0]);
        int minute=Integer.parseInt(parts[1]);
        int second=Integer.parseInt(parts[2]);
        int totalSeconds=hour*3600+minute*60+second;
        return totalSeconds;
    }
}

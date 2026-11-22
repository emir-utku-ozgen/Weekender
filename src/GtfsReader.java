import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GtfsReader {
    public static void printStopTimes(String path) {
        int totalCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String header = reader.readLine();
            String[] columns = header.split(",");
            int tripCol = -1;
            int depCol = -1;
            int stopCol = -1;
            for (int i = 0; i < columns.length; i++) {
                if (columns[i].equals("trip_id")) {
                    tripCol = i;
                }
                if (columns[i].equals("departure_time")) {
                    depCol = i;
                }
                if (columns[i].equals("stop_id")) {
                    stopCol = i;
                }
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                totalCount++;
                if (totalCount <= 5) {
                    String trip = values[tripCol];
                    String stop = values[stopCol];
                    String dep = values[depCol];
                    int seconds = GtfsTime.parseTime(dep);
                    System.out.println(
                            "trip=" + trip +
                            "  stop=" + stop +
                            "  dep=" + dep +
                            " (" + seconds + ")"
                    );
                }
            }
            System.out.println("Total rows: " + totalCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

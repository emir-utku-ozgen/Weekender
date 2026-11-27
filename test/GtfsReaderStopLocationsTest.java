import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GtfsReaderStopLocationsTest {

    @Test
    void parsesLatLonForKnownStopsAndSkipsUnknownOnes(@TempDir Path tempDir) throws IOException {
        Path stopsFile = tempDir.resolve("stops.txt");
        Files.writeString(stopsFile,
                "stop_name,parent_station,stop_id,stop_lat,stop_lon,location_type,platform_code\n" +
                "Aachen Hbf,,S1,50.7678,6.091499,,\n" +
                "Berlin Hbf,,S2,52.525589,13.369549,,\n" +
                "Unused Stop,,S3,48.1,11.5,,\n"
        );

        Map<String, Integer> stopIndex = new HashMap<>();
        stopIndex.put("S1", 0);
        stopIndex.put("S2", 1);
        // S3 kasıtlı olarak stopIndex'te yok (hiçbir bağlantıda kullanılmamış gibi davranıyoruz)

        Map<Integer, StopLocation> locations = GtfsReader.readStopLocations(stopsFile.toString(), stopIndex);

        assertEquals(2, locations.size());
        assertEquals(new StopLocation(50.7678, 6.091499), locations.get(0));
        assertEquals(new StopLocation(52.525589, 13.369549), locations.get(1));
        assertFalse(locations.containsKey(2));
    }
}

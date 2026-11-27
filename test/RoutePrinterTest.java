import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoutePrinterTest {

    private final ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void redirectSystemOut() {
        System.setOut(new PrintStream(outputBuffer));
    }

    @AfterEach
    void restoreSystemOut() {
        System.setOut(originalOut);
    }

    @Test
    void printsEmptyRouteMessageWhenRouteIsEmpty() {
        Journey journey = new Journey(List.of(), Integer.MAX_VALUE);

        RoutePrinter.print(journey);

        assertEquals("Rota Bulunamadı" + System.lineSeparator(), outputBuffer.toString());
    }

    @Test
    void printsEachConnectionAndArrivalTime() {
        Connection c1 = new Connection(0, 1, 100, 200, "trip1");
        Connection c2 = new Connection(1, 2, 250, 300, "trip2");
        Journey journey = new Journey(List.of(c1, c2), 300);

        RoutePrinter.print(journey);

        String output = outputBuffer.toString();
        assertTrue(output.contains("trip=trip1"));
        assertTrue(output.contains("trip=trip2"));
        assertTrue(output.contains("0 → 1"));
        assertTrue(output.contains("1 → 2"));
        assertTrue(output.contains("Varış: " + GtfsTime.formatTime(300)));
    }
}

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.io.*;
import java.util.*;

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
    public static List<Connection> readConnections(String path,Map<String,Integer> stopIndex) {

        List<Connection> connections = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String header = reader.readLine();
            String[] columns = header.split(",");
            int tripCol = -1;
            int arrCol = -1;
            int depCol = -1;
            int stopCol = -1;
            int i;
            for (i = 0; i < columns.length; i++) {
                if (columns[i].equals("trip_id")) {
                    tripCol = i;
                }
                if (columns[i].equals("arrival_time")) {
                    arrCol = i;
                }
                if (columns[i].equals("departure_time")) {
                    depCol = i;
                }
                if (columns[i].equals("stop_id")) {
                    stopCol = i;
                }
            }
            String line;
            String prevTrip = null;
            int prevStop = -1;
            int prevDep = -1;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                String trip = values[tripCol];
                String stop = values[stopCol];
                String arr = values[arrCol];
                String dep = values[depCol];
                int arrSeconds = GtfsTime.parseTime(arr);
                int depSeconds = GtfsTime.parseTime(dep);
                if (!stopIndex.containsKey(stop)) {
                    stopIndex.put(stop, stopIndex.size());
                }
                int stopIdx = stopIndex.get(stop);
                if (trip.equals(prevTrip)) {

                    connections.add(
                            new Connection(
                                    prevStop,
                                    stopIdx,
                                    prevDep,
                                    arrSeconds,
                                    trip
                            )
                    );
                }
                prevTrip = trip;
                prevStop = stopIdx;
                prevDep = depSeconds;
            }
        }catch(IOException e){
                e.printStackTrace();
            }
        return connections;
        }
    public static Map<Integer, StopLocation> readStopLocations(String path, Map<String, Integer> stopIndex){
        Map<Integer,StopLocation> locations= new HashMap<>();
        try(BufferedReader reader=new BufferedReader(new FileReader(path))){
            String header=reader.readLine();
            String []columns=header.split(",");
            int idCol=-1;
            int latCol=-1;
            int lonCol=-1;
            int i;
            for(i=0;i<columns.length;i++){
                if(columns[i].equals("stop_id")){
                    idCol=i;
                }
                if(columns[i].equals("stop_lat")){
                    latCol=i;
                }
                if(columns[i].equals("stop_lon")){
                    lonCol=i;
                }
            }
            String line ;
            while((line=reader.readLine())!=null){
                String [] values=line.split(",");
                String stopId=values[idCol];
                if(!stopIndex.containsKey(stopId)){
                    continue;
                }
                int idx=stopIndex.get(stopId);
                double lat = Double.parseDouble(values[latCol]);
                double lon = Double.parseDouble(values[lonCol]);
                locations.put(idx, new StopLocation(lat, lon));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return locations;
    }





















    }


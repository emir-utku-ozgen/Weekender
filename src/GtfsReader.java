import javax.swing.*;
import java.time.LocalDate;
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
    public static Map<String,String> readTripServices(String path){
        Map<String,String> tripServices= new HashMap<>();
        try(BufferedReader reader=new BufferedReader(new FileReader(path))){
            String header= reader.readLine();
            String []columns=header.split(",");
            int tripCol=-1,serviceCol=-1;
            int i;
            for(i=0;i<columns.length;i++){
                if(columns[i].equals("trip_id")){
                    tripCol=i;
                }
                if(columns[i].equals("service_id")) {
                    serviceCol = i;
                }
            }
            String line;
            while((line=reader.readLine())!=null){
                String [] values=line.split(",");
                tripServices.put(values[tripCol],values[serviceCol]);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return tripServices;
    }
    public static Map<String, Calendar> readCalendars(String path) {
        Map<String, Calendar> calendars = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String header = reader.readLine();
            String[] columns = header.split(",");
            int monCol=-1, tueCol=-1, wedCol=-1, thuCol=-1, friCol=-1, satCol=-1, sunCol=-1, startCol=-1, endCol=-1, serviceCol=-1;
            for (int i = 0; i < columns.length; i++) {
                switch (columns[i]) {
                    case "monday" -> monCol = i;
                    case "tuesday" -> tueCol = i;
                    case "wednesday" -> wedCol = i;
                    case "thursday" -> thuCol = i;
                    case "friday" -> friCol = i;
                    case "saturday" -> satCol = i;
                    case "sunday" -> sunCol = i;
                    case "start_date" -> startCol = i;
                    case "end_date" -> endCol = i;
                    case "service_id" -> serviceCol = i;
                }
            }
            String line;
            while ((line = reader.readLine()) != null) {
                String[] v = line.split(",");
                Calendar calendar = new Calendar(
                        v[monCol].equals("1"), v[tueCol].equals("1"), v[wedCol].equals("1"),
                        v[thuCol].equals("1"), v[friCol].equals("1"), v[satCol].equals("1"), v[sunCol].equals("1"),
                        LocalDate.parse(v[startCol], java.time.format.DateTimeFormatter.BASIC_ISO_DATE),
                        LocalDate.parse(v[endCol], java.time.format.DateTimeFormatter.BASIC_ISO_DATE)
                );
                calendars.put(v[serviceCol], calendar);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return calendars;
    }

    public static Map<String, Map<LocalDate, Integer>> readCalendarExceptions(String path) {
        Map<String, Map<LocalDate, Integer>> exceptions = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String header = reader.readLine();
            String[] columns = header.split(",");
            int serviceCol = -1, typeCol = -1, dateCol = -1;
            for (int i = 0; i < columns.length; i++) {
                if (columns[i].equals("service_id")) serviceCol = i;
                if (columns[i].equals("exception_type")) typeCol = i;
                if (columns[i].equals("date")) dateCol = i;
            }
            String line;
            while ((line = reader.readLine()) != null) {
                String[] v = line.split(",");
                String serviceId = v[serviceCol];
                int type = Integer.parseInt(v[typeCol]);
                LocalDate date = LocalDate.parse(v[dateCol], java.time.format.DateTimeFormatter.BASIC_ISO_DATE);

                exceptions.computeIfAbsent(serviceId, k -> new HashMap<>()).put(date, type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exceptions;
    }



















    }


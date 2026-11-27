import java.util.*;
import java.time.LocalDate;
public class TripCalendar {
    private final Map<String,String> tripServices;
    private final Map<String,Calendar> calendars;
    private final Map<String, Map<LocalDate,Integer>> exceptions;
    public TripCalendar(Map<String,String> tripServices,Map<String,Calendar> calendars,Map<String, Map<LocalDate,Integer>> exceptions){
        this.tripServices=tripServices;
        this.calendars=calendars;
        this.exceptions=exceptions;
    }
    public boolean isTripActiveOn(String tripId,LocalDate date){
        String serviceId=tripServices.get(tripId);
        Map<LocalDate,Integer> serviceExceptions=exceptions.get(serviceId);
        if(serviceExceptions!=null && serviceExceptions.containsKey(date)){
            return serviceExceptions.get(date)==1;
        }
        Calendar calendar=calendars.get(serviceId);
        if(calendar==null){
            return false;
        }
        return calendar.isActiveOn(date);
    }



}

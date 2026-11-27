public class RoutePrinter {
    public static void print(Journey journey){
        if(journey.route().isEmpty()){
            System.out.println("Rota Bulunamadı");
            return ;
        }
        for(Connection c:journey.route()){
            System.out.println(
                    GtfsTime.formatTime(c.depTime()) + " → " +
                            GtfsTime.formatTime(c.arrTime()) +
                            "  trip=" + c.tripId() +
                            "  " + c.fromStop() + " → " + c.toStop()
            );
        }
        System.out.println("Varış: " + GtfsTime.formatTime(journey.arrivalTime()));
    }
}

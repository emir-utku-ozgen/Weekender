public class RoutePrinter {
    public static void print(Journey journey){
        if(journey.route().isEmpty()){
            System.out.println("Rota Bulunamadı");
            return ;
        }
        for(Connection c:journey.route()){
            System.out.println(
                    GtfsTime.formatAbsolute(c.depTime()) + " → " +
                            GtfsTime.formatAbsolute(c.arrTime()) +
                            "  trip=" + c.tripId() +
                            "  " + c.fromStop() + " → " + c.toStop()
            );
        }
        System.out.println("Varış: " + GtfsTime.formatAbsolute(journey.arrivalTime()));
        System.out.println("Tahmini fiyat: " + String.format("%.2f", journey.estimatedCost()) + " €");
    }
}

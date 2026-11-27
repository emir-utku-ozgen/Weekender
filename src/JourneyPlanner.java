import java.util.*;
public class JourneyPlanner {
    public static Journey plan(List<Connection> connections,int stopCount,int source,int target,int startTime,Map<Integer,StopLocation> locations){
        CsaResult result=Csa.run(connections,stopCount,source,startTime);
        List<Connection> route=Csa.buildRoute(result,target);
        double estimatedCost=PriceEstimator.estimateRouteCost(route,locations);
        int arrivalTime=result.earliestArrival()[target];
        return new Journey(route,arrivalTime,estimatedCost);

    }
}

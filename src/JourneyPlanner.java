import java.util.*;
public class JourneyPlanner {
    public static Journey plan(List<Connection> connections,int stopCount,int source,int target,int startTime){
        CsaResult result=Csa.run(connections,stopCount,source,startTime);
        List<Connection> route=Csa.buildRoute(result,target);
        int arrivalTime=result.earliestArrival()[target];
        return new Journey(route,arrivalTime);

    }
}

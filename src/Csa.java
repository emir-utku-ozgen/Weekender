import java.util.*;
public class Csa {
    public static CsaResult run(List<Connection> connections,int stopCount,int source,int startTime){
        int [] earliestArrival=new int[stopCount];
        Connection[] lastConnection=new Connection[stopCount];
        Arrays.fill(earliestArrival, Integer.MAX_VALUE);
        earliestArrival[source]=startTime;
        for(Connection c:connections){
            if(earliestArrival[c.fromStop()]<=c.depTime() && c.arrTime()<=earliestArrival[c.toStop()]){
                earliestArrival[c.toStop()]=c.arrTime();
                lastConnection[c.toStop()]=c;
            }
        }
        return new CsaResult(earliestArrival,lastConnection);
    }
    public static List<Connection> buildRoute(CsaResult result,int target){
        List<Connection> route=new ArrayList<>();
        int stop=target;
        while(result.lastConnection()[stop]!=null){
            Connection c=result.lastConnection()[stop];
            route.add(c);
            stop=c.fromStop();
        }
        Collections.reverse(route);
        return route;
    }

}
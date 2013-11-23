package Server;

import akka.actor.UntypedActor;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import Util.*;

import java.util.concurrent.Future;

public class BroadcastOutMain extends UntypedActor {

    Broadcaster b;

    public BroadcastOutMain() {
        this.b = BroadcasterFactory.getDefault().lookup("/");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        Data msg = (Data)message;
        Future br = b.broadcast("{\"message\": " + wrapInQuotes(msg) + "," +
                "\"who\":\""+msg.getWho()+"\","+
                "\"type\":\"northPole\"}");
    }

    private String wrapInQuotes(Data data){
        return ("Delivery".equals(data.getType()) ? data.getMessage() : "\"" + data.getMessage()+ "\" " );
    }
}

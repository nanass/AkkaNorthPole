package Server;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import Util.*;

import java.util.concurrent.Future;

public class BroadcastOutMain extends UntypedActor {

    Broadcaster b;
    private ActorRef input;

    public BroadcastOutMain() {
        this.b = BroadcasterFactory.getDefault().lookup("/");
        this.input = getContext().actorOf(Props.create(InputActor.class, "5563", getSelf()));
        input.tell("Start", getSelf());
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

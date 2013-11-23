package Server;

import akka.actor.UntypedActor;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import Util.*;

public class BroadcastOutMain extends UntypedActor {

    Broadcaster b;

    public BroadcastOutMain() {
        this.b = BroadcasterFactory.getDefault().lookup("/");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        Data msg = (Data)message;
        b.broadcast( "{\"message\":\"" + msg.getMessage() + "\"," +
                      "\"who\":\"" + msg.getWho() + "\"}"
        );
    }
}

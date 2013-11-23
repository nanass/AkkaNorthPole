package Server;

import akka.actor.ActorRef;
import org.atmosphere.config.service.Get;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;
import Util.*;

@ManagedService(path = "/northpole")
public class NorthPole {

    private final ObjectMapper mapper = new ObjectMapper();
    Broadcaster b = BroadcasterFactory.getDefault().get("/");

    @Get
    public void onOpen(final AtmosphereResource r) {
        b.addAtmosphereResource(r);
    }

    @Message
    public String onMessage(String message) throws IOException {
        Data d = mapper.readValue(message, Data.class);
        InjectWishList.wishList.tell(d, null);
        return mapper.writeValueAsString(d);
    }
    public static class InjectWishList{
        public static ActorRef wishList;
    }

}
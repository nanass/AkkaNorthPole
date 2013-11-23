package AkkaNorthPole.Main;

import AkkaNorthPole.Actors.*;
import AkkaNorthPole.Messages.Msg;
import AkkaNorthPole.Messages.NorthPoleMsg;
import Server.NettoServer;
import WishList.*;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class NorthPole{

    enum name{ Dasher, Dancer, Prancer, Vixen, Comet, Cupid, Donder, Blitzen, Ruldolph }

    public static void main(String[] args) throws IOException {
        NettoServer server = new NettoServer();
        List<ActorRef> elves = new ArrayList<ActorRef>();
        List<ActorRef> reindeer = new ArrayList<ActorRef>();

        ActorSystem system = ActorSystem.create("NorthPole");
        ActorRef wishList = system.actorOf(Props.create(WishList.class));
        Server.NorthPole.InjectWishList.wishList = wishList;
        ActorRef santa = system.actorOf(Props.create(Santa.class, "Santa", wishList));
        ActorRef waitingRoom = system.actorOf(Props.create(WaitingRoom.class, "WaitingRoom", santa));
        santa.tell(new Msg(NorthPoleMsg.WaitingRoom), waitingRoom);

        for (int i = 1; i <= 10; i++){
            elves.add(system.actorOf(Props.create(Elf.class, "Elf" + String.valueOf(i), waitingRoom)));
        }
        for (name r : name.values()){
            reindeer.add(system.actorOf(Props.create(Reindeer.class, r.toString(), waitingRoom)));
        }
        ActorRef mrsClaus = system.actorOf(Props.create(MrsClaus.class, santa));
    }
}

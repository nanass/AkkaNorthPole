package WishList;

import Util.Data;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import Server.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class WishList extends UntypedActor {

    private CopyOnWriteArrayList<Data> wishList = new CopyOnWriteArrayList<Data>();
    ActorRef broadcast;
    WishList(){
        broadcast = getContext().actorOf(Props.create(BroadcastOutMain.class));
    }

    @Override
    public void onReceive(Object o) throws Exception {

        if(o instanceof Data){
            Data d = (Data)o;
            if(d.getMessage().equals("Deliver")){
                deliverGifts();
            }
            else{
                addToWishList(d);
            }
        }
    }

    private void deliverGifts(){
        String output = "[";
        System.out.println("Gifts for: ");
        for(Data d : wishList){
            output += "\"" + d.getAuthor() + " : " + d.getMessage() + "\",";
        }
        wishList.clear();
        output = output.substring(0,output.length() - 1) + "]";
        System.out.println(output);
        Data data = new Data("all", output);
        data.setType("Delivery");
        broadcast.tell(data,getSelf());
    }

    private void addToWishList(Data msg){
        if(msg.getType().equals("wishlist")){
            wishList.add(msg);
            System.out.println(msg.getMessage());
        }
    }
}


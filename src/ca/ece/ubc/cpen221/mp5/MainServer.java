package ca.ece.ubc.cpen221.mp5;

public class MainServer {

    public void main(String[] args){
        RestaurantDBServer server = new RestaurantDBServer(Integer.parseInt(args[0]), args[1], args[2], args[3]);
//        
//        server.stopServerSocket();
//        return;

    }
}

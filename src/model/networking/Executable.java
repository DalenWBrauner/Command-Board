package model.networking;

import java.rmi.AccessException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import model.AIEasy;
import model.Match;
import model.MatchFactory;
import model.Player;
import shared.interfaces.PlayerRepresentative;

public class Executable {
    public final static String coordinatorName = "Coordinator";
    public final static int portNo = 64246;
    public static Server server;

    public static void main(String[] args) {
        // If we have arguments, execute the server too
        if (args.length > 0 && ("-s".equals(args[0]) || "--server".equals(args[0]))) {
            executeServer();
        }

        // Launch the Client
        try {
            executeClient(args);
            //executeClient2Player(args);
        } catch (Exception e) {
            System.err.println("Client exception:");
            e.printStackTrace();
        }
    }

    private static void executeServer() {
        System.out.println("Launching Server...");
        server = new Server();
        server.run();
    }

    public static void endServer() throws RemoteException, NotBoundException {
        server.endServer();
    }

    private static Registry connect(String[] args)
            throws AccessException, ConnectException, RemoteException {
        // Establish a registry
        Registry registry;

        // If they use the -c or --client arguments, assume the next argument is an IP address
        if (args.length > 1 && ("-c".equals(args[0]) || "--client".equals(args[0]))) {
            System.out.println("Connecting to "+args[1]+"...");
            registry = LocateRegistry.getRegistry(args[1], portNo);
        } else {
            System.out.println("Connecting to localhost...");
            registry = LocateRegistry.getRegistry(portNo);
        }
        // NOTE: we haven't actually connected to the registry, just
        // created an object that points to it.

        // Here's the part where we actually connect.
        registry.list(); // This will fail if there's no registry to connect to.
        return registry;
    }

    private static void executeClient(String[] args) throws RemoteException, NotBoundException {
        System.out.println("Launching Client...");
        Registry registry;
        try {
            registry = connect(args);
        } catch (ConnectException e) {
            System.out.println("Client unable to connect to Server!");
            e.printStackTrace();
            return;
        }
        System.out.println("Client successfully connected to Server!");

        // Get the coordinator
        Coordinator coordinator = (Coordinator) registry.lookup(coordinatorName);

        // Ask to play
        System.out.println("Can I play?");
        int myID = coordinator.canIPlay();
        if (myID < 0) {
            System.out.println("Aww, the game was full...");
            return; // If I can't, leave
        }
        System.out.println("YES! I'm player "+String.valueOf(myID)+"!");
        
        // Create our arbitrary match
        long seed = coordinator.getSeed();
        System.out.println("The coordinator's seed is "+String.valueOf(seed));
        MatchFactory mFactory = new MatchFactory();
        Match theMatch = mFactory.createMatch(4, 6000, "Keyblade", seed);
        
        // Get all our Player Objects
        ArrayList<Player> ourPlayers = theMatch.getAllPlayers();

        // Create all of our players' representatives
        int maxPlayers = coordinator.maxPlayers();
        PlayerRepresentative[] players = new PlayerRepresentative[maxPlayers];
        for (int i = 0; i < maxPlayers; i++) {

            // If it's us, create a local wrapped in a spy
            if (i == myID) {
                players[i] = new SpyPlayer(coordinator,
                        new AIEasy(ourPlayers.get(i)), i);
                System.out.println("Creating Spy with ID "+String.valueOf(i));

            } else {
                // Create server players
                players[i] = new ServerPlayer(coordinator, i);
                System.out.println("Creating ServerPlayer with ID "+String.valueOf(i));
            }
            
            // Set our representatives
            ourPlayers.get(i).setRepresentative(players[i]);
        }

        // Make sure everyone's ready...
        System.out.println("Ready...");
        coordinator.imReady(myID);

        // Then START!
        System.out.println("PLAYING!");
        theMatch.run();

        // Once the game is over
        System.out.println("Good game, everyone!");
        coordinator.goodGame(myID);

    }

    @SuppressWarnings("unused")
    private static void executeClient2Player(String[] args) throws RemoteException, NotBoundException {
        System.out.println("Launching Client...");
        Registry registry;
        try {
            registry = connect(args);
        } catch (ConnectException e) {
            System.out.println("Client unable to connect to Server!");
            e.printStackTrace();
            return;
        }
        System.out.println("Client successfully connected to Server!");

        // Get the coordinator
        Coordinator coordinator = (Coordinator) registry.lookup(coordinatorName);
        
        // Some prep
        int maxPlayers = coordinator.maxPlayers();
        int[] myIDs = new int[2];

        // Startup 2 players
        for (int player = 0; player < 2; player++) {
            // Ask to play
            System.out.println("Can I play?");
            myIDs[player] = coordinator.canIPlay();
            if (myIDs[player] < 0) {
                System.out.println("Aww, the game was full...");
                return; // If I can't, leave
            }
            System.out.println("YES! I'm player "+String.valueOf(myIDs[player])+"!");
        }

        // Create our arbitrary match
        long seed = coordinator.getSeed();
        System.out.println("The coordinator's seed is "+String.valueOf(seed));
        MatchFactory mFactory = new MatchFactory();
        Match theMatch = mFactory.createMatch(4, 6000, "Keyblade", seed);
        
        // Get all our Player Objects
        ArrayList<Player> ourPlayers = theMatch.getAllPlayers();

        // Create all of our players' representatives
        PlayerRepresentative[] players = new PlayerRepresentative[maxPlayers];
        for (int i = 0; i < maxPlayers; i++) {

            // If it's us, create a local wrapped in a spy
            if (i == myIDs[0] || i == myIDs[1]) {
                players[i] = new SpyPlayer(coordinator,
                        new AIEasy(ourPlayers.get(i)), i);

            } else {
                // Create server players
                players[i] = new ServerPlayer(coordinator, i);
            }
            
            // Set our representatives
            ourPlayers.get(i).setRepresentative(players[i]);
        }

        // Make sure everyone's ready...
        System.out.println("Ready...");
        coordinator.imReady(myIDs);

        // Then START!
        System.out.println("PLAYING!");
        theMatch.run();

        // Once the game is over
        System.out.println("Good game, everyone!");
        coordinator.goodGame(myIDs);
    }
}

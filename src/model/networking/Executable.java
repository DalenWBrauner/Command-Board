package model.networking;

import java.rmi.AccessException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import model.Match;
import model.MatchFactory;
import model.Player;
import shared.interfaces.AIEasy;
import shared.interfaces.PlayerRepresentative;

public class Executable {
    public static final String coordinatorName = "Coordinator";
    public static int portNo = 64246;
    public static Server server;

    public static void main(String[] args) {
        // If we have arguments, execute the server too
        if (parseLaunchServer(args)) {
            executeServer();
        }
        // Launch the Client
        try {
            executeClient(args);
        } catch (Exception e) {
            System.err.println("Client exception:");
            e.printStackTrace();
        }
    }

    /** Find and return the position of a given argument from the command line */
    private static int findArg(String[] args, String findMe) {
        for (int i = 0; i < args.length; i++) {
            if (findMe.equals(args[i])) return i;
        }
        return -1; // If we didn't find it
    }

    /** Determine from the arguments whether or not we're launching the server. */
    private static boolean parseLaunchServer(String[] args) {
        if (findArg(args, "-s") > -1
         || findArg(args, "--server") > -1) return true;
        else return false;
    }

    /** Determine from the arguments the number of local players in our game. */
    private static int parseNumberPlaying(String[] args) {
        int terse = findArg(args,"-p");
        int verbose = findArg(args,"--players");
        if (terse > -1) {
            try { return Integer.valueOf(args[terse+1]); }
            catch (Exception e) { return 1; }
        } else if (verbose > -1) {
            try { return Integer.valueOf(args[verbose+1]); }
            catch (Exception e) { return 1; }
        } else return 1;
    }

    /** Determine from the arguments the IP address we're connecting to. */
    private static String parseIPAddress(String[] args) {
        int terse = findArg(args, "-ip");
        if (terse > -1) return args[terse+1];
        else return "";
    }

    /** Launch the Server thread */
    private static void executeServer() {
        System.out.println("Launching Server...");
        server = new Server();
        server.run();
    }

    /** End the Server thread */
    public static void endServer() throws RemoteException, NotBoundException {
        server.endServer();
    }

    private static Registry connect(String[] args)
            throws AccessException, ConnectException, RemoteException {
        // Determine who we're connecting to
        String IP = parseIPAddress(args);

        // Establish a registry
        Registry registry;
        if (IP.equals("")) {
            System.out.println("Connecting to localhost...");
            registry = LocateRegistry.getRegistry(portNo);
        } else {
            System.out.println("Connecting to "+IP+"...");
            registry = LocateRegistry.getRegistry(IP, portNo);
        }
        // NOTE: we haven't actually connected to the registry, just
        // created an object that points to it.

        // Here's the part where we actually connect.
        try {
            registry.list(); // This will fail if there's no registry to connect to.
        } catch (ConnectException e) {
            System.out.println("Client unable to connect to Server!");
            e.printStackTrace();
            registry = null;
        }
        return registry;
    }

    private static void executeClient(String[] args) throws RemoteException, NotBoundException {
        System.out.println("Launching Client...");

        // Get the registry
        Registry registry = connect(args);
        if (registry == null) return; // If the connection failed
        System.out.println("Client successfully connected to Server!");

        // Get the coordinator
        Coordinator coordinator = (Coordinator) registry.lookup(coordinatorName);

        // Get the number of local players
        int numPlaying = parseNumberPlaying(args);

        // Ask if they can play
        System.out.println("Can I play?");
        int[] myIDs = coordinator.canIPlay(numPlaying);
        if (myIDs[0] < 0) {
            System.out.println("Aww, the game was full...");
            return; // If I can't, leave
        }
        System.out.println("YES! We are players:");
        for (int ID : myIDs) System.out.println(String.valueOf(ID));

        // Create our match
        long seed = coordinator.getSeed();
        System.out.println("The coordinator's seed is "+String.valueOf(seed));
        MatchFactory mFactory = new MatchFactory();
        // We are currently using arbitrary arguments
        Match theMatch = mFactory.createMatch(4, 6000, "Keyblade", seed);

        // Get all our Player Objects
        ArrayList<Player> ourPlayers = theMatch.getAllPlayers();

        // Create all of our players' representatives
        int maxPlayers = coordinator.maxPlayers();
        PlayerRepresentative[] players = new PlayerRepresentative[maxPlayers];
        for (int i = 0; i < maxPlayers; i++) {

            // Check if it's one of us
            boolean localPlayer = false;
            for (int ID : myIDs) {
                if (i == ID) localPlayer = true;
            }

            // If it is, create a local wrapped in a spy
            if (localPlayer) players[i] = new SpyPlayer(
                    coordinator, new AIEasy(ourPlayers.get(i)), i);

            // Create server players
            else players[i] = new ServerPlayer(coordinator, i);

            // Set our representative
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

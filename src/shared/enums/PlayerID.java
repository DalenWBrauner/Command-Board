package shared.enums;

public enum PlayerID {

    NOPLAYER ("Nobody"),
    PLAYER1  ("Player 1"),
    PLAYER2  ("Player 2"),
    PLAYER3  ("Player 3"),
    PLAYER4  ("Player 4");

    private final String myName;
    private PlayerID(String name) {
        myName = name;
    }

    @Override
    public String toString() {
        return myName;
    }

    /** Returns an array of n PlayerIDs in ID order, */
    public static PlayerID[] getNPlayers(int n) {
        PlayerID[] players;
        if        (n == 1) {
            players = new PlayerID[]{ PLAYER1 };
        } else if (n == 2) {
            players = new PlayerID[]{ PLAYER1, PLAYER2 };
        } else if (n == 3) {
            players = new PlayerID[]{ PLAYER1, PLAYER2, PLAYER3 };
        } else if (n == 4) {
            players = new PlayerID[]{ PLAYER1, PLAYER2, PLAYER3, PLAYER4 };
        } else {
            players = new PlayerID[]{ NOPLAYER };
        }
        return players;
    }
}

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
}

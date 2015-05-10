package shared.enums;

public enum CardinalDirection {

    NORTH ("North"),
    SOUTH ("South"),
    EAST  ("East"),
    WEST  ("West"),
    //Helps JavaFX avoid null pointers.
    NONE ("None");

    private final String myName;
    private CardinalDirection(String name) {
        myName = name;
    }

    @Override
    public String toString() {
        return myName;
    }

}

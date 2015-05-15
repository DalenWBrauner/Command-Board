package shared.enums;

public enum CardinalDirection {

    NORTH ("North"),
    SOUTH ("South"),
    EAST  ("East"),
    WEST  ("West"),
    NONE  ("None");

    private final String myName;
    private CardinalDirection(String name) {
        myName = name;
    }

    @Override
    public String toString() {
        return myName;
    }

}

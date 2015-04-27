package shared.enums;

public enum TileType {

    NONE        ("Nonexistant"),
    PROPERTY    ("Property"),
    START       ("Start"),
    CHECKPOINT  ("Checkpoint");

    private final String myName;
    private TileType(String name) {
        myName = name;
    }

    @Override
    public String toString() {
        return myName;
    }

}

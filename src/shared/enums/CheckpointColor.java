package shared.enums;

public enum CheckpointColor {

    RED ("Red"),
    BLU ("Blue"),
    GRN ("Green"),
    YLW ("Yellow");

    private final String myName;
    private CheckpointColor(String name) {
        myName = name;
    }

    @Override
    public String toString() {
        return myName;
    }

}

package shared.enums;

public enum CardShape {

    NOCARD ("None"),
    SHAPE1 ("Circle"),
    SHAPE2 ("Square"),
    SHAPE3 ("Triangle");

    private final String myName;
    private CardShape(String name) {
        myName = name;
    }

    @Override
    public String toString() {
        return myName;
    }

}


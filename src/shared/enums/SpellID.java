package shared.enums;

public enum SpellID {

    NOSPELL ("No Spell"),
    SPELL1  ("Spell 1"),
    SPELL2  ("Spell 2"),
    SPELL3  ("Spell 3"),
    SPELL4  ("Spell 4"),
    SPELL5  ("Spell 5"),
    SPELL6  ("Spell 6"),
    SPELL7  ("Spell 7"),
    SPELL8  ("Spell 8");

    private final String myName;
    private SpellID(String name) {
        myName = name;
    }

    @Override
    public String toString() {
        return myName;
    }
}

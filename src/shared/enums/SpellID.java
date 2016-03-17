package shared.enums;

public enum SpellID {

    NOSPELL ("No Spell"),
    SPELL1  ("Navigator"),
    SPELL2  ("Foreclosure"),
    SPELL3  ("Upgrade"),
    SPELL4  ("Card Swap"),
    SPELL5  ("Player Swap"),
    SPELL6  ("Cash Magnet"),
    SPELL7  ("Spell 7"),
    SPELL8  ("Spell 8"),
    SPELL9  ("Spell 9"),
    SPELL10 ("Spell 10");

    private final String myName;
    private SpellID(String name) {
        myName = name;
    }

    @Override
    public String toString() {
        return myName;
    }

    public static SpellID fromString(String text) {
    	if (text != null) {
    		for (SpellID b : SpellID.values()) {
    			if (text.equalsIgnoreCase(b.myName)) {
    				return b;
    			}
    		}
    	}
    	return null;
     }
}

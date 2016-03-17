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
    
    public static TileType fromString(String text) {
    	if (text != null) {
    		for (TileType b : TileType.values()) {
    			if (text.equalsIgnoreCase(b.myName)) {
    				return b;
    			}
    		}
    	}
    	return null;
     }
}

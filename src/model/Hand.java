package model;

import java.util.ArrayList;
import java.util.Collection;

import shared.enums.CardShape;
import shared.enums.SpellID;

public class Hand {

    private Collection<CardShape> inHand;

    public SpellID[] getCastableSpells() {
        ArrayList<SpellID> castableSpells = new ArrayList<>();
        castableSpells.add(SpellID.NOSPELL);

        // Convert to an array
        SpellID[] retVal = new SpellID[castableSpells.size()];
        castableSpells.toArray(retVal);
        return retVal;
    }
}

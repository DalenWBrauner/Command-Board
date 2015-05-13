package model;

import java.util.ArrayList;
import java.util.HashMap;

import model.command.Command;
import model.command.EnableWalkBackwardsCommand;
import shared.WatchTower;
import shared.enums.CardShape;
import shared.enums.PlayerID;
import shared.enums.SpellID;

public class SpellCaster {

    private final Board theBoard;
    private final HashMap<PlayerID, Player> playerMap;
    // The spellbook isn't static only because each command
    // is dependent on that match's watchtower
    private final HashMap<SpellID, Command> spellBook = new HashMap<>();
    private static final HashMap<SpellID, HashMap<CardShape, Integer>> spellCosts;
    static {
        spellCosts = new HashMap<>();
        spellCosts.put(SpellID.NOSPELL, new HashMap<>());
        spellCosts.put(SpellID.SPELL1, new HashMap<>());
        spellCosts.put(SpellID.SPELL2, new HashMap<>());
        spellCosts.put(SpellID.SPELL3, new HashMap<>());

        // The 'NoSpell' doesn't cost anything.

        // Spell 1 costs one Shape1 card
        spellCosts.get(SpellID.SPELL1).put(CardShape.SHAPE1, 1);

        // Spell 2 costs two Shape2 cards
        spellCosts.get(SpellID.SPELL2).put(CardShape.SHAPE2, 2);

        // Spell 3 costs one Shape2 and one Shape3 card
        spellCosts.get(SpellID.SPELL3).put(CardShape.SHAPE2, 1);
        spellCosts.get(SpellID.SPELL3).put(CardShape.SHAPE3, 1);
    }

    /** Creates a SpellCaster unique to this match. */
    public SpellCaster(WatchTower tower, Board board,
                       HashMap<PlayerID, Player> players) {
        theBoard = board;
        playerMap = players;
        spellCraft(tower);
    }

    /** Returns the number of each cardshape it costs to cast the spell. */
    public static HashMap<CardShape, Integer> getCost(SpellID spell) {
        return spellCosts.get(spell);
    }

    /** Prepares the Player to cast a spell if they can and they wish! */
    public void performMagic(Player player) {

        System.out.println("Player " + player.getID() + " has the hand:");
        for (CardShape card : player.getHand().getAllCards()) {
            System.out.println(card);
        }

        // First, get all of the possible spells that player could cast.
        SpellID[] castableSpells = getCastableSpells(player.getHand());

        System.out.println("and can cast the spells: ");
        for (SpellID spell : castableSpells) {
            System.out.println(spell);
        }

        // Second, ask that player which of those spells they'd like to cast.
        SpellID spellCast = player.getRepresentative().getSpellCast(castableSpells);

        System.out.println("They chose to cast "+spellCast+"!");

        // Third, cast that spell.
        cast(player, spellCast);
    }

    /** Returns the list of spells that can be cast with the cards in the given hand. */
    private SpellID[] getCastableSpells(Hand playersHand) {
        ArrayList<SpellID> castableSpells = new ArrayList<>();

        // Add any spells we can cast
        for (SpellID spell : spellCosts.keySet()) {
            if (canCastSpell(playersHand, spell)) castableSpells.add(spell);
        }

        // Convert to array
        SpellID[] arrayOfCastableSpells = new SpellID[castableSpells.size()];
        castableSpells.toArray(arrayOfCastableSpells);
        return arrayOfCastableSpells;
    }

    /** Returns whether the given hand has the cards to cast the given spell. */
    private boolean canCastSpell(Hand playersHand, SpellID spell) {
        HashMap<CardShape, Integer> cost = spellCosts.get(spell);

        // Check each shape needed against the number in the hand
        for (CardShape shape : cost.keySet()) {
            // If the cost is greater, we can't cast it.
            if (cost.get(shape) > playersHand.getNumberOfCards(shape)) {
                return false;
            }
        }

        // If none of the costs are too great, we can cast!
        return true;
    }

    /** Subtracts the cards necessary and casts the spell! */
    private void cast(Player player, SpellID spellCast) {
        // Don't bother if it's the nospell
        if (spellCast != SpellID.NOSPELL) {

            // First subtract their cards
            Hand playersHand = player.getHand();
            HashMap<CardShape, Integer> cost = spellCosts.get(spellCast);
            for (CardShape card : cost.keySet()) {
                playersHand.remove(card, cost.get(card));
            }

            // Then execute the comman- err, perform feats of magic!
            spellBook.get(spellCast).execute(player);
        }
    }

    /** Creates each spell and places them into the spellbook. */
    private void spellCraft(WatchTower tower) {
        spellBook.put(SpellID.SPELL1, craftNavigator(tower));
        spellBook.put(SpellID.SPELL2, craftNavigator(tower));
        spellBook.put(SpellID.SPELL3, craftNavigator(tower));
    }

    /** Returns the Navigator Spell */
    private Command craftNavigator(WatchTower tower) {
        Command navigator = new EnableWalkBackwardsCommand();
        navigator.addObserver(tower);
        return navigator;
    }
}

package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import model.command.AddFundsCommand;
import model.command.CashMagnetCommand;
import model.command.CastOnPlayerCommand;
import model.command.Command;
import model.command.EnableWalkBackwardsCommand;
import model.command.SellAnyTileCommand;
import model.command.SellTileCommand;
import model.command.SubtractFundsCommand;
import model.command.SwapCardAnyTileCommand;
import model.command.SwapCardCommand;
import model.command.UpgradeAnyTileCommand;
import model.command.UpgradeTileCommand;
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
        spellCosts.put(SpellID.SPELL4, new HashMap<>());
        spellCosts.put(SpellID.SPELL6, new HashMap<>());

        // (SHAPE1) Circle   Cards are seen as "Help" cards
        // (SHAPE2) Square   Cards are seen as "Wild" cards
        // (SHAPE3) Triangle Cards are seen as "Harm" cards

        // The 'NoSpell' doesn't cost anything.
        spellCosts.get(SpellID.NOSPELL).put(CardShape.SHAPE1, 0);
        spellCosts.get(SpellID.NOSPELL).put(CardShape.SHAPE2, 0);
        spellCosts.get(SpellID.NOSPELL).put(CardShape.SHAPE3, 0);

        // Navigator costs one Circle card
        spellCosts.get(SpellID.SPELL1).put(CardShape.SHAPE1, 1);
        spellCosts.get(SpellID.SPELL1).put(CardShape.SHAPE2, 0);
        spellCosts.get(SpellID.SPELL1).put(CardShape.SHAPE3, 0);

        // Foreclosure costs three Triangle cards
        spellCosts.get(SpellID.SPELL2).put(CardShape.SHAPE1, 0);
        spellCosts.get(SpellID.SPELL2).put(CardShape.SHAPE2, 0);
        spellCosts.get(SpellID.SPELL2).put(CardShape.SHAPE3, 3);

        // Upgrade costs three Circle cards
        spellCosts.get(SpellID.SPELL3).put(CardShape.SHAPE1, 3);
        spellCosts.get(SpellID.SPELL3).put(CardShape.SHAPE2, 0);
        spellCosts.get(SpellID.SPELL3).put(CardShape.SHAPE3, 0);

        // Card Swap costs one Square card
        spellCosts.get(SpellID.SPELL4).put(CardShape.SHAPE1, 0);
        spellCosts.get(SpellID.SPELL4).put(CardShape.SHAPE2, 1);
        spellCosts.get(SpellID.SPELL4).put(CardShape.SHAPE3, 0);

        // Cash Magnet costs one of each card
        spellCosts.get(SpellID.SPELL6).put(CardShape.SHAPE1, 1);
        spellCosts.get(SpellID.SPELL6).put(CardShape.SHAPE2, 1);
        spellCosts.get(SpellID.SPELL6).put(CardShape.SHAPE3, 1);
    }

    /** Creates a SpellCaster unique to this match. */
    public SpellCaster(WatchTower tower, Board board,
                       HashMap<PlayerID, Player> players) {
        theBoard = board;
        playerMap = players;
        spellCraft(tower);
    }

    /** Prepares the Player to cast a spell if they can and they wish! */
    public void performMagic(Player player) {

        System.out.println(player.getID() + " has the cards:");
        for (CardShape card : player.getHand().getAllCards()) {
            if (card != CardShape.NOCARD) System.out.println(card + " card");
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
        spellBook.put(SpellID.SPELL2, craftForeclosure(tower));
        spellBook.put(SpellID.SPELL3, craftUpgrade(tower));
        spellBook.put(SpellID.SPELL4, craftCardSwap(tower));
        spellBook.put(SpellID.SPELL6, craftCashMagnet(tower));
    }

    /** Returns the Navigator Spell */
    private Command craftNavigator(WatchTower tower) {
        Command spell = new EnableWalkBackwardsCommand();
        spell.addObserver(tower);
        return spell;
    }

    /** Returns the Foreclosure Spell */
    private Command craftForeclosure(WatchTower tower) {
        AddFundsCommand afc = new AddFundsCommand();
        SellTileCommand stc = new SellTileCommand(afc);
        Command foreclose = new SellAnyTileCommand(stc, theBoard);
        Command spell = new CastOnPlayerCommand(SpellID.SPELL2, foreclose, playerMap);

        spell.addObserver(tower);
        return spell;
    }

    /** Returns the Upgrade Spell */
    private Command craftUpgrade(WatchTower tower) {
        SubtractFundsCommand sfc = new SubtractFundsCommand();
        UpgradeTileCommand utc = new UpgradeTileCommand(sfc);
        Command spell = new UpgradeAnyTileCommand(utc, theBoard);

        spell.addObserver(tower);
        return spell;
    }

    /** Returns the Card Swap Spell */
    private Command craftCardSwap(WatchTower tower) {
        SwapCardCommand scc = new SwapCardCommand();
        Command spell = new SwapCardAnyTileCommand(scc, theBoard);

        spell.addObserver(tower);
        return spell;
    }

    /** Returns the Cash Magnet Spell */
    private Command craftCashMagnet(WatchTower tower) {
        AddFundsCommand afc = new AddFundsCommand();
        Command spell = new CashMagnetCommand(afc, 300, playerMap);

        spell.addObserver(tower);
        return spell;
    }

    // Getters

    /** Returns the number of each cardshape it costs to cast the spell. */
    public static HashMap<CardShape, Integer> getCost(SpellID spell) {
        return spellCosts.get(spell);
    }

    public static Set<SpellID> getSpellList() {
        return spellCosts.keySet();
    }
}

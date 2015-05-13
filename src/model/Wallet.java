package model;

import model.command.Command;
import model.command.NullCommand;
import model.tile.PropertyTile;

public class Wallet {

    private int cashOnHand;
    private final Player theOwner;
    private Command onNegative = new NullCommand();

    public Wallet(Player owner) {
        theOwner = owner;
        cashOnHand = 0;
    }

    public int getCashOnHand() {
        return cashOnHand;
    }

    public int getNetValue() {
        int netValue = cashOnHand;

        // Sum up the value of owned tiles
        for (PropertyTile asset : theOwner.getTilesOwned())
            netValue += asset.getValue();

        return netValue;
    }

    public void addFunds(int funds) {
        Math.max(funds, 0); // You can't add negative funds
        cashOnHand += funds;
    }

    public void subtractFunds(int funds) {
        Math.max(funds, 0); // You can't subtract negative funds
        cashOnHand -= funds;

        // If funds fall below zero:
        if (funds < 0) onNegative.execute(theOwner);
    }

    public void setOnNegativeCommand(Command command) {
        onNegative = command;
    }
}

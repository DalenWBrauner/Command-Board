package model;

public class ActualPlayer implements Player {

    private Hand myHand;
    private Wallet myWallet;

    @Override
    public Hand getHand() {
        return myHand;
    }

    @Override
    public Wallet getWallet() {
        return myWallet;
    }

}

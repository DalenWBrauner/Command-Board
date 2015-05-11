package model;

public class Wallet {

    private int cashOnHand = 0;
    private int netValue = 0;

    public int getCashOnHand() { return cashOnHand; }
    public int getNetValue() { return netValue; }
    public void setCashOnHand(int newValue) { cashOnHand = newValue; }
    public void setNetValue(int newValue) { netValue = newValue; }

}

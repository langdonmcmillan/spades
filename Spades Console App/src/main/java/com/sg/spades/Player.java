/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.spades;

/**
 *
 * @author apprentice
 */
public class Player {
    private String name;
    private CardHand playerHand;
    private int bet;
    private boolean nilBusted;
    
    public Player(String name) {
        this.name = name;
        playerHand = new CardHand();
        nilBusted = false;
    } 
    
    public boolean getNilBusted() {
        return nilBusted;
    }
    
    public void setNilBusted(boolean busted) {
        nilBusted = busted;
    }
    
    public int getBet() {
        return bet;
    }
    
    public void setBet(int bet) {
        this.bet = bet;
    }

    public String getName() {
        return name;
    }

    public CardHand getPlayerHand() {
        return playerHand;
    }
}

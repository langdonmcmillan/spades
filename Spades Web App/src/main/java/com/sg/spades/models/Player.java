/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.spades.models;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author apprentice
 */
public class Player {
    private int playerID;
    private String playerName;
    private int playerBid;
    private int playerTricks;
    private ArrayList<Card> hand;

    public Player(int playerID, String playerName) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.hand = new ArrayList<>();
    }
    
    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerBid() {
        return playerBid;
    }

    public void setPlayerBid(int playerBid) {
        this.playerBid = playerBid;
    }

    public int getPlayerTricks() {
        return playerTricks;
    }
    
    public void addPlayerTrick() {
        this.playerTricks++;
    }

    public void setPlayerTricks(int playerTricks) {
        this.playerTricks = playerTricks;
    }
    
    public boolean hasSuit(int suitValue) {
        return (hand.stream().filter(c -> c.getSuitValue() == suitValue).count() > 0);
    }
    
    public boolean hasNonSpadeSuit() {
        return (hand.stream().filter(c -> c.getSuitValue() != 3).count() > 0);
    }
    
    private void sortHand() {
        Collections.sort(hand, (c1, c2) -> Integer.valueOf(c1.getCardID()).compareTo(Integer.valueOf(c2.getCardID())));
    }

    public ArrayList<Card> getHand() {
        sortHand();
        return hand;
    }
    
}

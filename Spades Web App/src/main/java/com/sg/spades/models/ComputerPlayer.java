/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.spades.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import sun.security.provider.MD5;

/**
 *
 * @author apprentice
 */
public class ComputerPlayer extends Player {
    
    public ComputerPlayer(int playerID, String playerName) {
        super(playerID, playerName);
    }

    public void setPlayerBid() {
        double bid = 0;
        for (Card card: super.getHand()) {
            if (card.getCardValue() > 13) {
                bid++;
            } else if (card.getCardValue() > 10 && card.getSuitValue() == 3) {
                bid++;
            } else if (card.getCardValue() > 11){
                bid += .5;
            } else if (card.getCardValue() > 9){
                bid += .25;
            } else if (card.getSuitValue() == 3) {
                bid += .5;
            }         
        }
        super.setPlayerBid((int) bid);
    }
    
    public Card playCard(ArrayList<Card> currentPlay, ArrayList<Card> deck) {
       
        List<Card> validCards = super.getHand().stream().collect(Collectors.toList());
        
        if (currentPlay.isEmpty()) {
            if ((deck.stream().filter(c -> c.getSuitValue() == 3).count() == 0) && super.hasNonSpadeSuit()) {
                validCards = validCards.stream().filter(c -> c.getSuitValue() != 3).collect(Collectors.toList());
            }
        } else {
            if (super.hasSuit(currentPlay.get(0).getSuitValue())) {
                validCards = validCards.stream().filter(c -> c.getSuitValue().equals(currentPlay.get(0).getSuitValue())).collect(Collectors.toList());
            }
        }
        
        return validCards.get(validCards.size() - 1);
    }
}

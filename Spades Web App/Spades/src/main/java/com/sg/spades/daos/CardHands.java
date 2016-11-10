/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.spades.daos;

import com.sg.spades.models.Card;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author apprentice
 */
public class CardHands {
    private ArrayList<Card> deck;
    private HashMap<Integer, ArrayList<Card>> playerHand;
    
    public void shuffleDeck() {
        for (int suitValue = 0; suitValue <= 3; suitValue++) {
            for (int value = 2; value <= 14; value++) {
                deck.add(new Card(suitValue, value));
            }
        } 
    }
    
    public void dealCards() {
        Random r = new Random();
        for (int i = 0; i < 13; i++) {
            for (int playerID = 1; playerID <= 4; playerID++) {
                int pickedCard = r.nextInt(deck.size());
                playerHand.get(playerID).add(deck.get(pickedCard));
                deck.remove(pickedCard);
            }
        }
        sortHands();
        
    }
    
    private void sortHands() {
        for (int playerID: playerHand.keySet()) {
            playerHand.get(playerID).stream()
                    .sorted((card1, card2) -> card1.getCardID().compareTo(card2.getCardID()));
        }
    }
    
    public ArrayList<Card> getPlayerHand(int playerID) {
        return playerHand.get(playerID);
    }
    
    public Card playCard (int playerID, Card card) {
        playerHand.get(playerID).remove(card);
        return card;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.spades;

import java.util.ArrayList;

/**
 *
 * @author apprentice
 */
public class CardDeck {
    ArrayList<Card> deck;
    
    public CardDeck() {
        deck = new ArrayList<>();
        for (int suit = 0; suit < 4; suit++) {
            for (int value = 0; value < 13; value++) {
                deck.add(new Card(suit, value));
            }
        }
    }
    
    public void removeCard(Card card) {
        deck.remove(card);
    }
    
    public ArrayList<Card> getDeck() {
        return deck;
    }
}

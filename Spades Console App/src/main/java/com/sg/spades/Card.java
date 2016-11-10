/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.spades;

import java.util.Objects;

/**
 *
 * @author apprentice
 */
public class Card {
    private int suit;
    private int value;
    private int id;
    
    public Card(int suit, int value) {
        this.suit = suit;
        this.value = value;
        this.id = Integer.parseInt(Integer.toString(suit) + Integer.toString(value));
    }

    public int getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }
    
    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}

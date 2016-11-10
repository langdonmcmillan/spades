/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.spades.models;

import java.util.Objects;

/**
 *
 * @author apprentice
 */
public class Card {
    private Integer suitValue;
    private Integer cardValue;
    private Integer cardID;
    
    public Card(Integer suitValue, Integer cardValue){
    this.suitValue = suitValue;
    this.cardValue = cardValue;
    this.cardID = Integer.parseInt("" + suitValue + cardValue);
}
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.cardID);
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
        if (!Objects.equals(this.cardID, other.cardID)) {
            return false;
        }
        return true;
    }

    public Integer getSuitValue() {
        return suitValue;
    }

    public Integer getCardValue() {
        return cardValue;
    }

    public Integer getCardID() {
        return cardID;
    }
}

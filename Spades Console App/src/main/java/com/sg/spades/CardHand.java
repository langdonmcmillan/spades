/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.spades;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author apprentice
 */
public class CardHand {
    private String[][] deck = {
            { // Cubs
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" 
            },
            { // Diamonds
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"        
            },
            { // Hearts
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"        
            },
            { // Spades
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"        
            }
        };
    private ArrayList<Card> hand;
    private ConsoleIO console;
    
    public CardHand() {
        hand = new ArrayList<>();
        console = new ConsoleIO();
    }
    
    public void addCard(Card card) {
        hand.add(card);
    }
    
    public ArrayList<Card> getHand() {
        return hand;
    }
    
    public void removeCard(Card card) {
        hand.remove(card);
    }
    
    public boolean hasCard(Card card) {
        if (!hand.contains(card)) {
            console.printStringln("You do not have that card in your hand.");
        }
        return hand.contains(card);
    }
    
    public void drawCard(CardDeck deck) {
        int randomCard = pickRandomCard(deck);
        hand.add(deck.getDeck().get(randomCard));
        deck.getDeck().remove(randomCard);
    }
    
    private int pickRandomCard(CardDeck deck) {
        Random rand = new Random();
        return rand.nextInt(deck.getDeck().size());
    }
    
    public boolean hasOtherSuits() {
        boolean otherSuits = false;
        for (Card card: hand) {
            if (card.getSuit() < 3) {
                console.printStringln("You cannot lead with a spade until a spade"
                                      + " has already been played or you do not "
                                      + "have any other suits in your hand.");
                otherSuits = true;
                break;
            }
        }
        return otherSuits;
    }
    
    public boolean hasGivenSuit(int suit) {
        boolean givenSuit = false;
        for (Card card: hand) {
            if (card.getSuit() == suit) {
                givenSuit = true;
                break;
            }
        }
        return givenSuit;
    }
    
    public void sortHand() {
        for (int i = 0; i < hand.size() - 1; i++) {
            for (int j = i + 1; j < hand.size(); j++) {
                if (hand.get(i).getSuit() > hand.get(j).getSuit()) {
                    swap(i, j);
                }
                if (hand.get(i).getSuit() == hand.get(j).getSuit()
                    && hand.get(i).getValue() > hand.get(j).getValue()) {
                    swap(i, j);
                }
            }
        }
    }
    
    private void swap(int i, int j) {
        Card tempCard = hand.get(i);
        hand.set(i, hand.get(j));
        hand.set(j, tempCard);
    }
    
    public void printCards() {        
        printTopBottom();
        printSuit("left");
        printCardValue();
        printSuit("right");
        printTopBottom();
    }
    
    private void printTopBottom() {
        // I separated these out to be able to print the cards horizontally.
        for (int i = 0; i < hand.size(); i++) {
            console.printString("+-----+   ");
        }
        console.printStringln("");
    }
    
    private void printSuit (String location) {
        char club = '\u2663';
        char spade = '\u2660';
        char diamond = '\u2666';
        char heart = '\u2665';
        char[] cardSymbol= {
            club, diamond, heart, spade
        }; 
        if (location.equals("left"))
            for (Card card: hand) {
                console.printString("|" + cardSymbol[card.getSuit()] + "    |   ");
        } else {
            for (Card card: hand) {
                console.printString("|    " + cardSymbol[card.getSuit()] + "|   ");
            }
        }
        console.printStringln("");
    }
    
    private void printCardValue() {
        for (Card card: hand) {
            if (card.getValue() == 8) {
                console.printString("|  " + deck[card.getSuit()][card.getValue()] + " |   ");
            } else {
                console.printString("|  " + deck[card.getSuit()][card.getValue()] + "  |   ");
            }
        }
        console.printStringln("");
    }
}

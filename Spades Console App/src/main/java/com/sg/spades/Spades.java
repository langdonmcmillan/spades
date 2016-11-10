/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.spades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author apprentice
 */
public class Spades {
    private ConsoleIO console;
    private FileIO saveLoad;
    private ArrayList<Player> playerInfo;
    private CardDeck deck;
    private CardHand currentPlay;
    private Team team1;
    private Team team2;
    private boolean spadePlayed;
    private int leadSuit;
    private final String[] SUITS = {"CLUBS", "DIAMONDS", "HEARTS", "SPADES"};
    private final String[] VALUES = {"2", "3", "4", "5", "6", "7", "8", "9",
                                     "10", "Jack", "Queen", "King", "Ace"};
    
    public Spades() {
        console = new ConsoleIO();
        playerInfo = new ArrayList<>();
        team1 = new Team();
        team2 = new Team();
        leadSuit = 0;
        saveLoad = new FileIO();
    }
    
    public void startGame() {
        saveLoad.load();
        getTeams();
        playGame();
    }
    
    private void getTeams() {
        for (int i = 0; i < 4; i++) {
            String name = console.getString("Please enter player " + (i + 1) + "'s name");
            Player player = new Player(name);
            playerInfo.add(player);
            if (i % 2 == 0) {
                team1.addPlayer(player);
            } else {
                team2.addPlayer(player);
            }
        }
    }
    
    private void playGame() { 
        while (true) {
            startNewHand();
            for (int handNum = 0; handNum < 13; handNum++) {
                console.printStringln("Team 1 Bet: " + team1.getBet() + ", Tricks: " 
                                      + team1.getTricks()+ ", Score: " + team1.getScore());
                console.printStringln("Team 2 Bet: " + team2.getBet() + ", Tricks: " 
                                      + team2.getTricks()+ ", Score: " + team2.getScore());
                waitForUser();
                currentPlay = new CardHand();
                playHand();
                printCurrentPlay(4);
                determineWinner();
            }
                team1.updateScore();
                team2.updateScore();
        }
    }
    
    private void startNewHand() {
        if (team1.getScore() > 25 || team2.getScore() > 25) {
            endGame();
        }
        console.printStringln("Team 1 Score: " + team1.getScore() + ", Bags: " + team1.getBags());
        console.printStringln("Team 2 Score: " + team2.getScore() + ", Bags: " + team2.getBags());
        team1.setTricks(0);
        team2.setTricks(0);
        spadePlayed = false;
        for (Player player: playerInfo) {
            player.setNilBusted(false);
        }
        deck = new CardDeck(); 
        drawCards();
        getBets();
    }
    
    private void playHand() {
        for (int i = 0; i < 4; i++) {
            clearScreen();
            printCurrentPlay(i);
            waitForUser();
            playCard(playerInfo.get(i));
        }
        clearScreen();
    }
    
    private void drawCards() {
        for (int i = 0; i < 13; i++) {
            for (Player player: playerInfo) {
                player.getPlayerHand().drawCard(deck);
            }
        }
        for (Player player: playerInfo) {
            player.getPlayerHand().sortHand();
        }
    }
    
    private void getBets() {
        for (int i = 0; i < 4; i++) {
            // Print previous players' bets
            for(int j = 0; j < i; j++) {
                console.printStringln(playerInfo.get(j).getName() + ": Bet: " 
                                      + playerInfo.get(j).getBet());
            }
            playerInfo.get(i).getPlayerHand().printCards();
            playerInfo.get(i).setBet(console.getInteger(playerInfo.get(i).getName() + ", what would "
                                        + "you like to bet? (0 for nil)", 0, 13));
            clearScreen();
        }
        // Add bets of teammates to get team bets
        team1.setBet(playerInfo.get(0).getBet() + playerInfo.get(2).getBet());
        team2.setBet(playerInfo.get(1).getBet() + playerInfo.get(3).getBet());
    }
    
    private void playCard(Player player) {
        console.printStringln(player.getName() + "'s turn.");
        player.getPlayerHand().printCards();
        Card playedCard;
        while (true) {
            String input = console.getString("Which card would you like to play? "
                                         + "(Suit then Value, ie Clubs Ace, "
                                         + "Spades 5, Diamonds Queen)");
            playedCard = convertCard(input);
            if (checkCard(player, playedCard, input)) {
                break;
            }
        }
        currentPlay.addCard(playedCard);
        player.getPlayerHand().removeCard(playedCard);
    }
    
    private boolean checkCard(Player player, Card playedCard, String input) {
        boolean isValid = false;
        if (isValidCardInput(playedCard) && player.getPlayerHand().hasCard(playedCard)) {
            if (playerInfo.indexOf(player) == 0) {
                if (checkLeadCard(player, playedCard)) {
                    leadSuit = playedCard.getSuit();
                    isValid = true;
                }   
            } else {
                if (checkFollowingCard(player, playedCard)) {
                    isValid = true;
                }
            }
        }
        return isValid;
    }
    
    private boolean checkLeadCard(Player player, Card playedCard) {
        boolean isValid = false;
        if (playedCard.getSuit() == 3) {
            if (!player.getPlayerHand().hasOtherSuits() || spadePlayed) {
                spadePlayed = true;
                isValid = true;
            }
        } else {
            isValid = true;
        }
        return isValid;
    }
    
    private boolean checkFollowingCard(Player player, Card playedCard) {
        boolean isValid = false;
        if (player.getPlayerHand().hasGivenSuit(leadSuit)) {
            if (playedCard.getSuit() == leadSuit) {
                isValid = true;
            } else {
                console.printStringln("You must play the same suit as the lead.");
            }
        } else {
            isValid = true;
            if (playedCard.getSuit() == 3) {
                spadePlayed = true;
            }
        }
        return isValid;
    }
    
    private boolean isValidCardInput(Card playedCard) {
        boolean isValid = true;
        if (playedCard.getSuit() == 55 || playedCard.getValue() == 55) {
            isValid = false;
            console.printStringln("That is not a valid card. Make sure to follow "
                                  + "the correct format.");
        }
        return isValid;
    }
    
    private Card convertCard(String input) {
        String[] chosenCard = input.split(" ");
        Card playedCard = new Card(55, 55);
        if (chosenCard.length > 1) {
            int cardSuit = convertSuit(chosenCard[0]);
            int cardValue = convertValue(chosenCard[1]);
            playedCard = new Card(cardSuit, cardValue);
            }
        return playedCard;
    }
    
    private int convertSuit(String cardSuit) {
        for (int i = 0; i < SUITS.length; i++) {
            if (SUITS[i].equalsIgnoreCase(cardSuit)) {
                return i;
            }
        }
        return 55;
    }
    
    private int convertValue(String cardValue) {
        for (int i = 0; i < VALUES.length; i++) {
            if (VALUES[i].equalsIgnoreCase(cardValue)) {
                return i;
            }
        }
        return 55;
    }

    private void rotatePlayers(int winner) {
        Player tempPlayer = new Player("Temp");
        for (int i = 0; i < winner; i++) {
            tempPlayer = playerInfo.get(0);
            for (int j = 0; j < 3; j++) {
                playerInfo.set(j, playerInfo.get(j + 1));
            }
            playerInfo.set(3, tempPlayer);
        }
    }
    
    private void clearScreen() {
        for (int i = 0; i < 100; i++) {
            console.printStringln("");
        }
    }
    
    private void printCurrentPlay(int currentPlayer) {
        if (currentPlay.getHand().size() > 0) {
            for (int i = 0; i < currentPlayer; i++) {
                if (playerInfo.get(i).getName().length() >= 9) {
                    console.printString(playerInfo.get(i).getName().substring(0, 8));
                } else {
                    console.printString(playerInfo.get(i).getName());
                    for (int j = 0; j < (10 - playerInfo.get(i).getName().length()); j++) {
                        console.printString(" ");
                    }
                }  
            }
            console.printStringln("");
            currentPlay.printCards();
        }    
    }

    private void determineWinner() {
        int winningCard = 0;
        for (int i = 1; i < 4; i++) {
            winningCard = updateWinner(winningCard, i);
        }
        console.printStringln("The winner of this hand is " + playerInfo.get(winningCard).getName());
        if (team1.getTeam().contains(playerInfo.get(winningCard))) {
            team1.addTrick();
        } else {
            team2.addTrick();
        }
        if (playerInfo.get(winningCard).getBet() == 0) {
            playerInfo.get(winningCard).setNilBusted(true);
        }
        rotatePlayers(winningCard);
        
    }
    
    private int updateWinner(int winningCard, int currentCard) {
        int winningSuit = currentPlay.getHand().get(winningCard).getSuit();
        int winningValue = currentPlay.getHand().get(winningCard).getValue();
        int currentSuit = currentPlay.getHand().get(currentCard).getSuit();
        int currentValue = currentPlay.getHand().get(currentCard).getValue();
        if (winningSuit == currentSuit) {
            if (currentValue > winningValue) {
                winningCard = currentCard;
            } 
        }
        if (currentSuit == 3 && winningSuit != 3) {
            winningCard = currentCard;
        }
        return winningCard;
    }
    
    private void waitForUser() {
        console.getStringEmpty("Press enter to go to the next player or hand.");
    }

    private void endGame() {
        console.printStringln("Team 1 Score: " + team1.getScore() + ", Bags: " + team1.getBags());
        console.printStringln("Team 2 Score: " + team2.getScore() + ", Bags: " + team2.getBags());
        if (team1.getScore() > team2.getScore()) {
            console.printStringln("The winning team is Team 1!");
        } else {
            console.printStringln("The winning team is Team 2!");
        }
        saveLoad.save(team1, team2);
        System.exit(0);
    }
}

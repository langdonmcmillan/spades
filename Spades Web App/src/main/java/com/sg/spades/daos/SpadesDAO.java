/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.spades.daos;

import com.sg.spades.models.Card;
import com.sg.spades.models.ComputerPlayer;
import com.sg.spades.models.Player;
import com.sg.spades.models.Team;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author apprentice
 */
public class SpadesDAO {

    private Team team1;
    private Team team2;
    private ArrayList<Player> playerList;
    private ArrayList<Card> deck;
    private ArrayList<Card> currentPlay;

    public SpadesDAO() {

    }

    public void setUpGame(String playerName) {
        deck = new ArrayList<>();
        team1 = new Team();
        team2 = new Team();
        playerList = new ArrayList<>();
        currentPlay = new ArrayList<>();
        addPlayers(playerName);
        buildDeck();
        dealCards();
    }

    public ArrayList<Card> getPlayerCards() {
        return playerList.get(0).getHand();
    }

    public void startNewHand() {
        team1.updateScore();
        team2.updateScore();
        for (Player player : playerList) {
            player.setPlayerBid(0);
            player.getHand().stream().filter(c -> currentPlay.contains(c)).forEach(c -> player.getHand().remove(c));
        }
        currentPlay.clear();
        team1.setTeamBid();
        team2.setTeamBid();
        dealCards();
    }

    private void addPlayers(String playerName) {
        playerList.add(new Player(1, playerName));
        playerList.add(new ComputerPlayer(2, "Opponent 1"));
        playerList.add(new ComputerPlayer(3, "Teammate"));
        playerList.add(new ComputerPlayer(4, "Opponent 2"));
        for (Player player : playerList) {
            if (player.getPlayerID() % 2 != 0) {
                team1.addPlayer(player);
            } else {
                team2.addPlayer(player);
            }
        }
    }

    private void buildDeck() {
        for (int suitValue = 0; suitValue < 4; suitValue++) {
            for (int cardValue = 2; cardValue <= 14; cardValue++) {
                deck.add(new Card(suitValue, cardValue));
            }
        }
    }

    private void dealCards() {
        Collections.shuffle(deck);
        for (int i = 0; i < 13; i++) {
            for (int playerIndex = 0; playerIndex <= 3; playerIndex++) {
                Card card = deck.get(0);
                playerList.get(playerIndex).getHand().add(card);
                deck.remove(card);
            }
        }
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public ArrayList<Card> getCurrentPlay() {
        return currentPlay;
    }

    public void addBids(int playerBid) {
        playerList.stream().filter(p -> p.getPlayerID() == 1).findAny().get().setPlayerBid(playerBid);
        for (Player player: playerList) {
            if (player instanceof ComputerPlayer) {
                ((ComputerPlayer) player).setPlayerBid();
            }
        }
    }
}

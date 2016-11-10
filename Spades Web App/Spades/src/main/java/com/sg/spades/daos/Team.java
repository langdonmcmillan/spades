/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.spades.daos;

import com.sg.spades.models.Player;
import java.util.ArrayList;

/**
 *
 * @author apprentice
 */
public class Team {
    private ArrayList<Player> team;
    private int teamBid;
    private int teamTricks;
    private int teamScore;
    private int teamBags;
    
    public void addPlayer(Player player) {
        team.add(player);
    }
    
    public void setTeamBid(int playerOneBid, int playerTwoBid) {
        teamBid = playerOneBid + playerTwoBid;
    }
    
    public void addTrick() {
        teamTricks++;
    }
    
    public void updateScore() {
        if (teamTricks >= teamBid) {
            teamScore += (teamBid * 10) + (teamTricks - teamBid);
            teamBags += (teamTricks - teamBid);
        } else {
            teamScore -= (teamBid * 10);
        }
        for ()
    }
}

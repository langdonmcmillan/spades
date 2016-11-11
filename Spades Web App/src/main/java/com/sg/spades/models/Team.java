/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.spades.models;

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
    
    public Team() {
        this.teamScore = 0;
        this.teamBid = 0;
        this.teamTricks = 0;
        this.teamBags = 0;
        this.team = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        team.add(player);
    }

    public void setTeamBid() {
        teamBid = team.stream().mapToInt(p -> p.getPlayerBid()).sum();
    }

    public void addTrick() {
        teamTricks++;
    }

    public void updateScore() {
        checkTricks();
        checkNil();
        checkBags();
    }

    private void checkTricks() {
        if (teamTricks >= teamBid) {
            teamScore += (teamBid * 10) + (teamTricks - teamBid);
            teamBags += (teamTricks - teamBid);
        } else {
            teamScore -= (teamBid * 10);
        }
    }

    private void checkNil() {
        for (Player player: team) {
            if (player.getPlayerBid() == 0) {
                if (player.getPlayerTricks() > 0) {
                    teamScore -= 100;
                } else {
                    teamScore += 100;
                }
            }
        }
    }

    private void checkBags() {
        if (teamBags >= 10) {
            teamScore -= 100;
            teamBags -= 10;
        }
    }

    public ArrayList<Player> getTeam() {
        return team;
    }

    public int getTeamBid() {
        return teamBid;
    }

    public int getTeamTricks() {
        return teamTricks;
    }

    public int getTeamScore() {
        return teamScore;
    }

    public int getTeamBags() {
        return teamBags;
    }
}

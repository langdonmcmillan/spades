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
public class Team {
    private ArrayList<Player> team;
    private int bet;
    private int bags;
    private int score;
    private int tricks;
    
    public Team() {
        team = new ArrayList();
        bet = 0;
        bags = 0;
    }
    
    public ArrayList<Player> getTeam() {
        return team;
    }
    
    public void addPlayer(Player player) {
        team.add(player);
    }
    
    public void setBet(int bet) {
        this.bet = bet;
    }
    
    public int getBet() {
        return bet;
    }
    
    public int getBags() {
        return bags;
    }
    
    public void addTrick() {
        setTricks(getTricks() + 1);
    }
    
    public int getScore() {
        return score;
    }
    
    public void updateScore() {
        if (tricks >= bet) {
            score += (bet * 10) + (tricks - bet);
            bags += (tricks - bet);
        } else {
            score -= (bet * 10);
        }
        for (Player player: team) {
            if (player.getNilBusted()) {
                score -= 100;
            } else if (player.getBet() == 0) {
                score += 100;
            }
        }
        if (bags >= 10) {
            score -= 100;
            bags -= 10;
        }
        
    }
    
    public void setScore(int score) {
        this.score = score;
    }

    public int getTricks() {
        return tricks;
    }

    public void setTricks(int tricks) {
        this.tricks = tricks;
    }
}

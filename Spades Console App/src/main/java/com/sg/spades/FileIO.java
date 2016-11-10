/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.spades;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author apprentice
 */
public class FileIO {
    private final String DELIMITER = "::";
    private ArrayList<String> encodedData;
    private ConsoleIO console;
    private Team team1;
    private Team team2;
    
    public FileIO() {
        console = new ConsoleIO();
        encodedData = new ArrayList<>();
        team1 = new Team();
        team2 = new Team();
    }
    
    public void save(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
        try {
            PrintWriter out = new PrintWriter(new FileWriter("lastResult.txt"));
            encode();
            for (String encodedLine: encodedData) {
                out.println(encodedLine);
            }
            out.flush();
        } catch (IOException ex) {
            console.printStringln("Uh oh, something went wrong");
        }
    }
    
    public void load() {
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader("lastResult.txt")));
            while (sc.hasNextLine()) {
                encodedData.add(sc.nextLine());
            }
            decode(team1, 0);
            decode(team2, 1);
            printPreviousResult();
        } catch (FileNotFoundException ex) {
            console.printStringln("This is the first game");
        } catch (Exception e) {
            console.printStringln("This is the first game");
        }
    }
    
    private void encode() {
        encodedData.add("Team 1" + DELIMITER + team1.getTeam().get(0).getName() + DELIMITER 
                        + team1.getTeam().get(1).getName() + DELIMITER + team1.getScore());
        encodedData.add("Team 2" + DELIMITER + team2.getTeam().get(0).getName() + DELIMITER 
                        + team2.getTeam().get(1).getName() + DELIMITER + team2.getScore());
    }
    
    private void decode(Team team, int index) {
        String[] splitEncodedLine = encodedData.get(index).split(DELIMITER);
        team.addPlayer(new Player(splitEncodedLine[1]));
        team.addPlayer(new Player(splitEncodedLine[2]));
        team.setScore(Integer.parseInt(splitEncodedLine[3]));
        
    }
    
    private void printPreviousResult() {
        console.printStringln("Result of last game:");
        console.printStringln("Team 1: " + team1.getTeam().get(0).getName() 
                              + " and " + team1.getTeam().get(1).getName() 
                              + ", Score: " + team1.getScore());
        console.printStringln("Team 2: " + team2.getTeam().get(0).getName() 
                              + " and " + team2.getTeam().get(1).getName() 
                              + ", Score: " + team2.getScore());
        if (team1.getScore() > team2.getScore()) {
            console.printStringln("The winning team was Team 1!\n");
        } else {
            console.printStringln("The winning team was Team 2!\n");
        }
    }
}

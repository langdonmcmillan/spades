package com.sg.spades;

import com.sg.spades.daos.SpadesDAO;
import com.sg.spades.models.Card;
import com.sg.spades.models.ComputerPlayer;
import com.sg.spades.models.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class SpadesController {

    SpadesDAO dao;

    public SpadesController() {
        dao = new SpadesDAO();
    }

    @RequestMapping(value = {"/enterPlayers"}, method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Player> startGame(@RequestBody String playerName) {
        dao.setUpGame(playerName);
        return dao.getPlayerList();
    }

    @RequestMapping(value = {"/", "/spades"}, method = RequestMethod.GET)
    public String displayHome(Map<String, Object> model) {
        return "spades";
    }

    @RequestMapping(value = {"/getCards"}, method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Card> getPlayerCards() {
        return dao.getPlayerCards();
    }

    private boolean validCard(Card card) {
        boolean isValid = false;
        // If this card is the lead card, the card is valid only if
        // 1) It is not a spade OR
        // 2) A spade has already been played OR
        // 3) The player only has spades left in his or her hand
        if (dao.getCurrentPlay().isEmpty()) {
            return (card.getSuitValue() != 3
                    || dao.getDeck().stream().filter(c -> c.getSuitValue() == 3).count() > 0)
                    || !dao.getPlayerList().get(0).hasNonSpadeSuit();
        }
        // If the card is not the lead card, the card is valid if
        // 1) The card is the same suit as the lead card OR
        // 2) The player does not have any cards of the same suit as the lead
        return (card.getSuitValue() == dao.getCurrentPlay().get(0).getSuitValue()
                || !dao.getPlayerList().get(0).hasSuit(dao.getCurrentPlay().get(0).getSuitValue()));
    }

    public void getPlayerBid(int playerID, int playerBid) {
        dao.getPlayerList().get(playerID - 1).setPlayerBid(playerBid);
    }

    @RequestMapping(value = {"/playCard/{cardID}"}, method = RequestMethod.POST)
    @ResponseBody
    public Card playCard(@PathVariable("cardID") String cardID) {
        Card card = new Card(cardID);
        if (dao.getCurrentPlay().size() < 4 && validCard(card)) {
            dao.getCurrentPlay().add(card);
            return card;
        } else {
        return new Card(-1, -1);
        }
    }

    @RequestMapping(value = {"/playHand"}, method = RequestMethod.GET)
    @ResponseBody
    public HashMap<Integer, Card> playHand() {
        if (dao.getCurrentPlay().size() < 4) {
            Player currentPlayer = dao.getPlayerList().get(dao.getCurrentPlay().size());
            HashMap<Integer, Card> cardPlayed = new HashMap<>();
            Card card;
            if (currentPlayer instanceof ComputerPlayer) {
                card = ((ComputerPlayer) currentPlayer).playCard(dao.getCurrentPlay(), dao.getDeck());
                dao.getCurrentPlay().add(card);
            } else {
                card = new Card(-1, -1);
            }
            cardPlayed.put(currentPlayer.getPlayerID(), card);
            return cardPlayed;
        } else {
            return new HashMap<>();
        }
    }

    @RequestMapping(value = {"/endHand"}, method = RequestMethod.GET)
    @ResponseBody
    public String determineWinner(int leadPlayerID) {
        Card winningCard = dao.getCurrentPlay().get(0);
        int winningCardIndex;
        for (Card card : dao.getCurrentPlay()) {
            if (card.getSuitValue() == winningCard.getSuitValue()) {
                winningCard = (card.getCardValue() > winningCard.getCardValue()) ? card : winningCard;
            } else if (card.getSuitValue() == 3) {
                winningCard = card;
            }
        }
        winningCardIndex = dao.getCurrentPlay().indexOf(winningCard);
        Collections.rotate(dao.getPlayerList(), -winningCardIndex);
        dao.startNewHand();
        return winningCard.getCardID();
    }

}

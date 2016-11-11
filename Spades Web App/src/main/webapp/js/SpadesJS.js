/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var playedCards;

$(document).ready(function () {
    playedCards = 1;
    
});

$("#startGameButton").click(function () {
    $.ajax({
        type: "POST",
        url: "enterPlayers",
        data: JSON.stringify(
                $("#playerName")
                ),
        contentType: "application/json; charset=utf-8",
        headers: {
            "Accept": "application/json",
            "Content-type": "application/json"
        },
        dataType: "json"
    }).success(function (data, status) {
        $("#playerNameForm").hide();
        $("#validationErrors").hide();
        $("#validationErrors").empty();
        loadCards();
    });
    playHand();
});

function loadCards() {
    $.ajax({
        type: "GET",
        url: "getCards"
    }).success(function (hand, status) {
        $(".playerCards").empty();
        $.each(hand, function (index, card) {
            $(".playerCards").append("<img class='card playerCard' id='" + card.cardID + "' src='" + imgPath + card.cardID + ".png' alt='Card'>");
        });
        playerTurn();
    });
}

$(document).on("click", ".clickableCard", function () {
    $.ajax({
        type: "POST",
        url: "playCard/" + $(this).attr("id"),
        data: JSON.stringify(
                $("#playerName")
                ),
        contentType: "application/json; charset=utf-8",
        headers: {
            "Accept": "application/json",
            "Content-type": "application/json"
        },
        dataType: "json"
    }).success(function (card, status) {
        if (card.suitValue > 0) {
            playedCards++;
            $("#" + card.cardID).hide();
            $("#playedCard").append("<img class='card playedCard' id='" + card.cardID + "' src='" + imgPath + card.cardID + ".png' alt='Card'>");
            $("img").each(function () {
                $(this).removeClass("clickableCard");
            });
            playHand();
        } else {
            alert("That is not a valid card!");
        }
    });
});

function playHand() {
    $.ajax({
        type: "GET",
        url: "playHand"
    }).success(function (map, status) {
        $.each(map, function (index, card) {
            if (index == 2) {
                $("#opponentCardLeft").append("<img class='card' id='" + card.cardID + "' src='" + imgPath + card.cardID + ".png' alt='Card'>");
                nextPlayer();
            } else if (index == 3) {
                $("#teammateCard").append("<img class='card' id='" + card.cardID + "' src='" + imgPath + card.cardID + ".png' alt='Card'>");
                nextPlayer();
            } else if (index == 4) {
                $("#opponentCardRight").append("<img class='card' id='" + card.cardID + "' src='" + imgPath + card.cardID + ".png' alt='Card'>");
                nextPlayer();
            }
        });
    });
}

function nextPlayer() {
    playedCards++;
    setTimeout(playHand, 3000);
}

function playerTurn() {
    $("img").each(function () {
        if ($(this).hasClass("playerCard")) {
            $(this).addClass("clickableCard");
        }
    });
}

function checkCards() {
    if (playedCards == 4) {
        $.ajax({
        type: "GET",
        url: "endHand"
    }).success(function (map, status) {
        
    });
    }
}
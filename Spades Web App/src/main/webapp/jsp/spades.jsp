
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Spades</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/SpadesCSS.css" rel="stylesheet">
    </head>
    <body>
        <div class='container-fluid'>
            <h1 class='text-center'>Spades</h1>
            <div id="playerNameForm" class="row">
                <div class="col-sm-offset-4 col-sm-4">
                    <label for="player1Name">Player 1:</label>
                    <input id="player1Name" type="text" class="form-control playerName" placeholder="Player 1"/>
                </div>
                <div class="col-sm-offset-4 col-sm-4 top-buffer text-center">           
                    <input id="startGameButton" class="btn btn-primary" value="Start Game"/>
                </div>
            </div>
            <div id="currentPlay" class="row">
                <div class="row">
                    <div id="teammateCard" class="text-center col-sm-offset-5 col-sm-2"></div>
                </div>
                <div class="row">
                    <div id="opponentCardLeft" class="col-sm-offset-2 col-sm-2 text-center"></div>
                    <div id="opponentCardRight" class="col-sm-offset-4 col-sm-2 text-center"></div>
                </div>
                <div class="row">
                    <div id="playedCard" class="text-center col-sm-offset-5 col-sm-2"></div>
                </div>
            </div>
            <div id="playerCards" class="playerCards text-center" class="row">
            </div>
            <script src="${pageContext.request.contextPath}/js/jquery-2.2.4.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
            <script>var imgPath = "${pageContext.request.contextPath}/img/";</script>
            <script src="js/SpadesJS.js"></script>
    </body>
</html>
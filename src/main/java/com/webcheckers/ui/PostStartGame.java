package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.Game;
import com.webcheckers.model.Color;
import com.webcheckers.model.Player;

import spark.*;

import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * @author Elyse Castles
 */
public class PostStartGame implements Route {

    //Attributes
    private static final Logger LOG = Logger.getLogger(PostStartGame.class.getName());
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    //Constructor

    /**
     * The constructor for the {@code POST / start game} route handler.
     *
     * @param lobby          from this lobby
     * @param templateEngine for the application to use when rendering HTML responses
     */
    public PostStartGame(PlayerLobby lobby, TemplateEngine templateEngine) {
        this.playerLobby = lobby;
        this.templateEngine = templateEngine;

        LOG.config("PostStartGameRoute is initialized.");
    }

    /**
     * Function returns boolean if both players are free to start a new game
     *
     * @param one player one
     * @param two player two
     * @return boolean
     */
    private boolean canStartGame(Player one, Player two) {
        return one.isPlayerFree() && two.isPlayerFree();
    }

    /**
     * Render the WebCheckers POST Start Game Route with two players
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return The rendered HTML for the Home page.
     */
    @Override
    public String handle(Request request, Response response) {
        LOG.config("PostStartGame is invoked.");
        final Session httpSession = request.session();
        Player red = httpSession.attribute(WebServer.PLAYER_KEY);
        final String opponentName = request.queryParams("username");
        Player white = playerLobby.getPlayerByName(opponentName);


        if (canStartGame(red, white) && white.getSpectating() == null) { //start new game
            Game newGame = new Game(red, white);
            red.setColor(Color.RED);
            red.startGame(newGame);
            white.startGame(newGame);
            white.setColor(Color.WHITE);

            response.redirect(WebServer.GAME_URL);
            halt();
            return null;
        } else if (white.getSpectating() != null) {
            httpSession.attribute(WebServer.GAME_OVER, "The player is currently spectating a game.");
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        } else { //player is busy, redirect home
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }
    }

}
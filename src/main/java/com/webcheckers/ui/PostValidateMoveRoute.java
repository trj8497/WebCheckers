package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

/**
 * @author Elyse Castles
 */
public class PostValidateMoveRoute implements Route {

    Game gameID;
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());
    private final Gson gson;

    /**
     * Constructor: save gson
     *
     * @param gson gson
     */
    public PostValidateMoveRoute(final Gson gson) {
        this.gson = gson;
        LOG.config("PostValidateMoveRoute is initialized.");
    }


    /**
     * Handle a move and check if it is a valid move
     *
     * @param request  Session Request
     * @param response Session Response
     * @return Message
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.config("PostValidateMoveRoute is invoked.");
        String query = request.queryParams("actionData"); //get gson
        Move move = gson.fromJson(query, Move.class); //get move object
        Session httpSession = request.session();
        Player one = httpSession.attribute(WebServer.PLAYER_KEY);
        Game game = one.getGame();
        return gson.toJson(game.takeTurn(move));
    }
}
package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.Game;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

/**
 * @author Tejaswini Jagtap
 * @author Elyse Castles
 */
public class PostBackupMoveRoute implements Route {

    Game gameID;
    private static final Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());
    private Gson gson;

    /**
     * Initialize PostBacUpMoveRoute
     *
     * @param gson gson
     */
    public PostBackupMoveRoute(Gson gson) {
        this.gson = gson;
        LOG.config("PostBackupMoveRoute is initialized.");
    }

    /**
     * Function handles a post request
     *
     * @param request  request
     * @param response response
     * @return Message
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostBackupMoveRoute is invoked.");
        Session httpSession = request.session();
        Player one = httpSession.attribute(WebServer.PLAYER_KEY);
        Game game = one.getGame();
        return gson.toJson(game.backMove());
    }

}

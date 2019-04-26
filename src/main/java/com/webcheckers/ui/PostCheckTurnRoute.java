package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Kayleigh Kolz
 */
public class PostCheckTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());
    private final Gson gson;

    /**
     * Create instance of PostCheckTurnRoute
     *
     * @param gson gson
     */
    public PostCheckTurnRoute(final Gson gson) {
        this.gson = gson;
        LOG.config("PostCheckTurnRoute is initialized.");
    }

    /**
     * Handle PostCheckTurnRoute call
     *
     * @param request  session request
     * @param response session response
     * @return gson Message
     */
    @Override
    public String handle(Request request, Response response) {
        LOG.config("PostCheckTurnRoute is invoked.");
        Session httpSession = request.session();
        Player p = httpSession.attribute(WebServer.PLAYER_KEY);
        Game game = p.getGame();

        if (game.checkResignations() || game.isGameOver()) {
            return gson.toJson(Message.info("true"));
        }
        if (game.getActiveColor() == p.getColor()) {
            return gson.toJson(Message.info("true"));
        }
        return gson.toJson(Message.info("false"));
    }

}
package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

/**
 * @author Kayleigh Kolz
 * @author Elyse Castles
 */
public class PostSubmitTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());
    private final Gson gson;

    /**
     * Create PostSubmitTurnRoute instance
     *
     * @param gson gson
     */
    public PostSubmitTurnRoute(final Gson gson) {
        this.gson = gson;
        LOG.config("PostSubmitTurnRoute is initialized.");
    }

    /**
     * Handle call to PostSubmitTrunRoute
     *
     * @param request  session request
     * @param response session response
     * @return json object
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.config("PostSubmitTurnRoute is invoked.");
        Session httpSession = request.session();
        Player p = httpSession.attribute(WebServer.PLAYER_KEY);
        Game game = p.getGame();

        Message m = game.submit();
        return gson.toJson(m);

    }

}
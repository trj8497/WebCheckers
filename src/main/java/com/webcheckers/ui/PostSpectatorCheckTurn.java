package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.Game;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.AI;
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
 * @author Tejaswini Jagtap
 * @author Himani Munshi
 * @author Elyse Castles
 */
public class PostSpectatorCheckTurn implements Route {

    private static final Logger LOG = Logger.getLogger(PostSpectatorCheckTurn.class.getName());
    private final Gson gson;
    private final PlayerLobby playerLobby;

    /**
     * Create instance of PostCheckTurnRoute
     *
     * @param gson gson
     */
    public PostSpectatorCheckTurn(final PlayerLobby lobby, final Gson gson) {
        this.gson = gson;
        LOG.config("PostSpectatorCheckTurn is initialized.");
        this.playerLobby = lobby;
    }

    /**
     * Handle PostSpectatorCheckTurnRoute call
     *
     * @param request  session request
     * @param response session response
     * @return gson Message
     */
    @Override
    public String handle(Request request, Response response) {
        LOG.config("PostSpectatorCheckTurn is invoked.");
        Session httpSession = request.session();
        Player spectator = httpSession.attribute(WebServer.PLAYER_KEY);
        Game gametospectate = spectator.getSpectating();


        if (gametospectate.getWhitePlayer() instanceof AI){
            return gson.toJson(Message.info("true"));
        }
        if (gametospectate.checkResignations() || gametospectate.isGameOver()) {
            return gson.toJson(Message.info("true"));
        }
        if (spectator.getLastColor() != gametospectate.getActiveColor()) {
            spectator.setLastColor(gametospectate.getActiveColor());
            return gson.toJson(Message.info("true"));
        }
        return gson.toJson(Message.info("false"));
    }

}
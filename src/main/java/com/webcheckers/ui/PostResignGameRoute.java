package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code POST /resignGame} route handler.
 *
 * @author Kayleigh Kolz
 * @author Konce Quispe
 * @author Elyse Castles
 */

public class PostResignGameRoute implements Route {

    private Gson gson;

    /**
     * Create PostResignGameRoute instance
     *
     * @param gson gson
     */
    PostResignGameRoute(Gson gson) {
        this.gson = gson;
    }

    /**
     * Handle call to PostResignGameRoute
     *
     * @param request  session request
     * @param response session response
     * @return json object
     */
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        final Session httpSession = request.session();
        Player player = httpSession.attribute(WebServer.PLAYER_KEY);
        Game game = player.getGame();
        game.resignGame(player);
        player.stopGame();
        Message stop = Message.info("Game successfully stopped.");

        return gson.toJson(stop);
    }

}

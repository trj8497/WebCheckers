package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.appl.Game;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Spectator's Game route.
 *
 * @author Tejaswini Jagtap
 * @author Himani Munshi
 * @author Elyse Castles
 */
public class GetSpectatorGame implements Route {

    //Attributes
    private static final Logger LOG = Logger.getLogger(GetSpectatorGame.class.getName());
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final Gson gson;


    //Constructor

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     * @param lobby          contains all the information of the users signed into the game
     * @param gson           gson
     */
    public GetSpectatorGame(final PlayerLobby lobby, final TemplateEngine templateEngine, final Gson gson) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.gson = gson;
        this.playerLobby = lobby;

        LOG.config("GetSpectatorGame is initialized.");
    }


    //Public methods

    /**
     * Render the WebCheckers Login page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return The rendered HTML for the Home page.
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSpectatorGame is invoked.");

        final Session httpSession = request.session();
        Player spectator = httpSession.attribute(WebServer.PLAYER_KEY);
        final String usertospectatename = request.queryParams("username");
        Player usertospectate = playerLobby.getPlayerByName(usertospectatename);
        Game gametospectate = usertospectate.getGame();
        spectator.setSpectating(gametospectate);
        Map<String, Object> vm = new HashMap<>();

        if (gametospectate == null) {
            httpSession.attribute(WebServer.GAME_OVER, "The Game ended.");
            spectator.setSpectating(null);
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        } else {

            vm.put("modeOptions", null);

        }

        vm.put("title", "Spectator Mode");
        vm.put("currentUser", usertospectate);
        vm.put("viewMode", viewMode.SPECTATOR);
        vm.put("redPlayer", gametospectate.getRedPlayer());
        vm.put("whitePlayer", gametospectate.getWhitePlayer());
        vm.put("activeColor", gametospectate.getActiveColor());
        vm.put("board", gametospectate.getBoardView());
        spectator.setLastColor(gametospectate.getActiveColor());

        // render the View
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));

    }

}
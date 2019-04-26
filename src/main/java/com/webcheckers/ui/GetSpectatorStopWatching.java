package com.webcheckers.ui;

import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import spark.*;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home route.
 *
 * @author Tejaswini Jagtap
 * @author Himani Munshi
 * @author Elyse Castles
 */
public class GetSpectatorStopWatching implements Route {

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
    public GetSpectatorStopWatching(final PlayerLobby lobby, final TemplateEngine templateEngine, final Gson gson) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.gson = gson;
        this.playerLobby = lobby;

        LOG.config("GetSpectatorStopWatching is initialized.");
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
        LOG.finer("GetSpectatorStopWatching is invoked.");

        final Session httpSession = request.session();
        Player spectator = httpSession.attribute(WebServer.PLAYER_KEY);
        spectator.setSpectating(null);
        response.redirect("/");
        halt();

        // render the View
        return null;

    }

}
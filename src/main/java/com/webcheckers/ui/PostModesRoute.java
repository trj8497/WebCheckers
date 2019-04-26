package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The modes route handler
 *
 * @author Jasmine Liang
 */
public class PostModesRoute implements Route {

    //Attributes
    private static final Logger LOG = Logger.getLogger(PostModesRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    //Constructor

    /**
     * The constructor for the {@code POST / modes} route handler.
     *
     * @param lobby          from this lobby
     * @param templateEngine for the application to use when rendering HTML responses
     */
    public PostModesRoute(PlayerLobby lobby, TemplateEngine templateEngine) {
        this.playerLobby = lobby;
        this.templateEngine = templateEngine;

        LOG.config("PostModesRoute is initialized.");
    }

    /**
     * Render the WebCheckers POST Modes Route
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return nothing
     */
    @Override
    public String handle(Request request, Response response) {
        LOG.config("PostModesRoute is invoked.");
        final Session httpSession = request.session();
        final String mode = request.queryParams("sub-circle");
        Player user = httpSession.attribute(WebServer.PLAYER_KEY);
        if (mode.equals("player")) {
            user.setMode("player");
        } else if (mode.equals("spec")) {
            user.setMode("spectator");
        }
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }
}

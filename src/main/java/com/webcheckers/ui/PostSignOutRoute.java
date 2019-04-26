package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import com.webcheckers.model.Player;

import java.util.*;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The {@code POST /signout} route handler.
 *
 * @author Konce Quispe
 * @author Kayleigh Kolz
 * @author Elyse Castles
 */
public class PostSignOutRoute implements Route {

    //Attributes
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    private final TemplateEngine templateEngine;
    public final PlayerLobby playerLobby;

    //Constructor

    /**
     * The constructor for the {@code POST / sign out} route handler.
     *
     * @param lobby          for this session
     * @param templateEngine for the application to use when rendering HTML responses
     */
    public PostSignOutRoute(PlayerLobby lobby, TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.playerLobby = lobby;
        LOG.finer("PostSignOutRoute is initialized.");
    }

    //Public methods

    /**
     * Render the WebCheckers POST Sign Out Route with a message to the signed out user.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return The rendered HTML for the Home page.
     */
    public String handle(Request request, Response response) {
        LOG.finer("PostSignOutRoute is invoked.");
        final Session httpSession = request.session();
        Player player = httpSession.attribute(WebServer.PLAYER_KEY);
        this.playerLobby.removePlayer(player.getName());
        request.session().removeAttribute(WebServer.PLAYER_KEY);

        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }

}



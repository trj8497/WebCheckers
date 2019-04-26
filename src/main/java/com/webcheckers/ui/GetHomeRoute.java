package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home page.
 *
 * @author Bryan Basham
 * @author Elyse Castles
 * @author Yu Liang
 */
public class GetHomeRoute implements Route {

    //Attributes
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    public final String TITLE_MESSAGE = "Welcome %s";
    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
    public static final String NUM_PLAYERS = "There are %d players online";
    public static final String NUM_MESSAGE1 = "There are no other players available to %s at this time.";
    public static final String NUM_MESSAGE2 = "";
    public static final String USER_ATTR = "currentUser";

    public static final String PLAYERS_ATTR = "numPlayers";
    public static final String NUM_ATTR = "numMessage";
    public static final String MSG_ATTR = "message";
    public static final String TITLE = "title";
    public static final String WELCOME_TITLE = "Welcome!";
    public static final String SESSION = "newSession";
    private static final String NAMES_ATT = "names";
    public Message loginSuccess = Message.info("Successfully logged in!");


    public static final String VIEW = "home.ftl";

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    //Constructor

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetHomeRoute(final PlayerLobby lobby, final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = lobby;
        //
        LOG.config("GetHomeRoute is initialized.");
    }

    //Public methods

    /**
     * Render the WebCheckers Home page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return The rendered HTML for the Home page.
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetHomeRoute is invoked.");

        final Session httpSession = request.session();
        Map<String, Object> vm = new HashMap<>();

        vm.put(MSG_ATTR, WELCOME_MSG); //display a user message in the Home page

        if (httpSession.attribute(WebServer.PLAYER_KEY) == null) {
            vm.put(TITLE, WELCOME_TITLE);

            vm.put(SESSION, true);
            vm.put(PLAYERS_ATTR, String.format(NUM_PLAYERS, this.playerLobby.numPlayers()));
            if (this.playerLobby.numPlayers() == 1) {
                vm.put(NUM_ATTR, NUM_MESSAGE1);
            } else {
                vm.put(NUM_ATTR, NUM_MESSAGE2);
            }
            return templateEngine.render(new ModelAndView(vm, "welcome.ftl"));
        } else {
            Player user = httpSession.attribute(WebServer.PLAYER_KEY);

            if (user.getSpectating() != null) {
                response.redirect(WebServer.SPECTATE_GAME);
                halt();
                return null;
            }
            vm.put(TITLE, String.format(TITLE_MESSAGE, user.getName()));
            vm.put(SESSION, false);
            if (httpSession.attribute(WebServer.GAME_OVER) != null) {
                vm.put(MSG_ATTR, Message.info(httpSession.attribute(WebServer.GAME_OVER)));
            } else {
                vm.put(MSG_ATTR, loginSuccess);
            }
            //check the mode
            vm.put("mode", user.getMode());

            vm.put(USER_ATTR, user);
            vm.put(NAMES_ATT, this.playerLobby.makeArray());

            if (!user.isPlayerFree()) { //if a player is free redirect to game
                response.redirect(WebServer.GAME_URL);
                halt();
                return null;
            }
            if (this.playerLobby.numPlayers() == 1) { //get correct number of players message
                if (user.getMode().equals("spectator")) {
                    vm.put(NUM_ATTR, String.format(NUM_MESSAGE1, "spectate"));
                } else {
                    vm.put(NUM_ATTR, String.format(NUM_MESSAGE1, "play"));
                }
            } else {
                vm.put(NUM_ATTR, NUM_MESSAGE2);
            }
        }

        // render the View
        return templateEngine.render(new ModelAndView(vm, VIEW));
    }
}
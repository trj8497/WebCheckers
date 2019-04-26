package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Person;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;
/**
 * The UI Controller to GET the Modes page.
 * @author Yu Liang
 */
public class GetModesRoute implements Route {
    //Attributes
    private static final Logger LOG = Logger.getLogger(GetModesRoute.class.getName());
    public final String TITLE_MESSAGE = "Choose a Mode to play";
    public static final String TITLE = "title";
    public static final String SESSION = "newSession";
    public static final String CURRENT_USER = "currentUser";


    public static final String VIEW = "modes.ftl";
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    //Constructor

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /modes} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetModesRoute(final PlayerLobby lobby, final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = lobby;
        //
        LOG.config("GetModesRoute is initialized.");
    }

    //Public methods

    /**
     * Render the WebCheckers Modes page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return The rendered HTML for the Modes page.
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetModesRoute is invoked.");
        final Session httpSession = request.session();
        Map<String, Object> vm = new HashMap<>();
        Player user = httpSession.attribute(WebServer.PLAYER_KEY);
        // render the View
        vm.put(TITLE, TITLE_MESSAGE);
        vm.put(SESSION, false);
        vm.put(CURRENT_USER, user);
        vm.put("redPlayer", new Person(" "));
        vm.put("whitePlayer", new Person(" "));
        return templateEngine.render(new ModelAndView(vm, VIEW));
    }
}

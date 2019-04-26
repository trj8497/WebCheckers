package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Login page.
 *
 * @author Konce Quispe
 * @author Kayleigh Kolz
 */
public class GetLogInRoute implements Route {

    //Attributes
    private static final Logger LOG = Logger.getLogger(GetLogInRoute.class.getName());
    public static final Message WELCOME_MSG = Message.info("Enter a username.");
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    public static final String TITLE = "title";
    public static final String MESSAGE = "message";
    public static final String VIEW_NAME = "login.ftl";
    public static final String LOGIN = "Login";

    //Constructor

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetLogInRoute(final PlayerLobby lobby, final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = lobby;

        LOG.config("GetLogInRoute is initialized.");
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
        LOG.finer("GetLogInRoute is invoked.");

        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE, LOGIN);

        vm.put(MESSAGE, WELCOME_MSG); //display a user message in the Home page

        // render the View
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }

}


package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Person;
import com.webcheckers.util.Message;
import spark.*;

import static spark.Spark.halt;

import com.webcheckers.model.Player;

import java.util.*;
import java.util.logging.Logger;

/**
 * The {@code POST /signin} route handler.
 *
 * @author Konce Quispe
 * @author Kayleigh Kolz
 * @author Elyse Castles
 */

public class PostSignInRoute implements Route {

    //Attributes
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    public static final String TITLE = "title";
    public static final String LOGIN_TITLE = "Login";
    public static final String MSG = "message";
    public static final String USER_NAME = "username";


    //Constructor

    /**
     * The constructor for the {@code POST / sign in} route handler.
     *
     * @param lobby          for this session.
     * @param templateEngine for the application to use when rendering HTML responses
     */
    public PostSignInRoute(PlayerLobby lobby, TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.playerLobby = lobby;
        LOG.finer("PostSignInRoute is initialized.");
    }

    //Public methods

    /**
     * If username is not valid, render the login page with invalid message.
     */
    private ModelAndView Alphanumeric(final Map<String, Object> vm) {
        Message alphanumeric = Message.error("Invalid username, please use only alphanumeric characters");

        vm.put(TITLE, LOGIN_TITLE);
        vm.put(MSG, alphanumeric);
        return new ModelAndView(vm, "login.ftl");
    }

    /**
     * Render the login page when the username is already taken.
     *
     * @param vm the view model
     * @return Reload the login page with a message to choose another username.
     */
    private ModelAndView nameTaken(final Map<String, Object> vm) {
        Message nameTaken = Message.error("Name has been taken, please choose another.");

        vm.put(TITLE, LOGIN_TITLE);
        vm.put(MSG, nameTaken);
        return new ModelAndView(vm, "login.ftl");
    }

    private ModelAndView emptyName(final Map<String, Object> vm) {
        Message emptyName = Message.error("Name can not be empty. Please enter a non-empty name.");

        vm.put(TITLE, LOGIN_TITLE);
        vm.put(MSG, emptyName);
        return new ModelAndView(vm, "login.ftl");
    }

    /**
     * Render the home page upon successful login.
     *
     * @param vm the view model
     * @return Render the home page with a message that you have successfully logged in.
     */
    public ModelAndView loginSuccess(final Map<String, Object> vm) {
        Message loginSuccess = Message.info("Successfully logged in!");

        vm.put(TITLE, "You're Logged In");
        vm.put(MSG, loginSuccess);
        printNames(vm);
        return new ModelAndView(vm, "home.ftl");
    }

    /**
     * Print the list of names of the current players.
     *
     * @param vm the view model
     */
    public void printNames(Map<String, Object> vm) {
        for (String name : this.playerLobby.getPlayerList().keySet()) {
            vm.put("tempPlayer", name);
        }
    }

    /**
     * Render the WebCheckers POST Sign In Route with a list of current players.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return The rendered HTML for the Home page.
     */
    @Override
    public String handle(Request request, Response response) {
        LOG.finer("PostSignInRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        ModelAndView mv;

        final Session httpSession = request.session();

        if (httpSession.attribute(WebServer.PLAYER_KEY) != null) { //nobody signed in
            response.redirect("/");
            halt();
            return null;
        }

        String name = request.queryParams(USER_NAME); //get username

        if (name.length() == 0) {
            mv = this.emptyName(vm);
            return templateEngine.render(mv);
        }

        if (name.matches("^[a-zA-Z0-9]*$") && !this.playerLobby.hasPlayer(name)) { //valid username
            Player player = new Person(name);
            this.playerLobby.addPlayer(player);
            httpSession.attribute(WebServer.PLAYER_KEY, player);

            response.redirect("/modes");
            halt();
            return null;

        } else if (!name.matches("^[a-zA-Z0-9]*$")) { //not alphanumeric
            mv = this.Alphanumeric(vm);
            return templateEngine.render(mv);

        } else { //name is already take
            mv = this.nameTaken(vm);
            return templateEngine.render(mv);
        }
    }

}
package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.appl.Game;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import spark.*;


import static java.lang.Boolean.TRUE;
import static spark.Spark.halt;


/**
 * The UI Controller to GET the Game route.
 *
 * @author Konce Quispe
 * @author Kayleigh Kolz
 * @author Elyse Castles
 * @author Tejaswini Jagtap
 * @author Himani Munshi
 */
public class GetGameRoute implements Route {

    //Attributes
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final Gson gson;

    //Constructor

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetGameRoute(final PlayerLobby lobby, final TemplateEngine templateEngine, final Gson gson) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.gson = gson;
        this.playerLobby = lobby;

        LOG.config("GetGameRoute is initialized.");
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
        LOG.finer("GetGameRoute is invoked.");

        final Session httpSession = request.session();
        Map<String, Object> vm = new HashMap<>();
        Player player = httpSession.attribute(WebServer.PLAYER_KEY);

        Game thisGame = player.getGame();
        final Map<String, Object> modeOptions = new HashMap<>(2);

        if (thisGame.checkResignations()) {
            httpSession.attribute(WebServer.GAME_OVER, "Sorry but " + thisGame.getLoser().getName() + " resigned the game. CONGRATULATIONS YOU WON!.");
            player.stopGame();
            response.redirect(WebServer.HOME_URL);
            return null;
        } else if (thisGame.isGameOver()) {
            String win;
            if (thisGame.getWinner() == player) {
                win = "Congratulations! You Won!";
            } else {
                win = "Sorry but " + thisGame.getWinner().getName() + " won the game. Better luck next time.";
            }
            httpSession.attribute(WebServer.GAME_OVER, win);
            player.stopGame();
            response.redirect(WebServer.HOME_URL);
            return null;
        } else {

            vm.put("modeOptions", null);

        }
        vm.put("title", "Game");
        vm.put("currentUser", player);
        vm.put("viewMode", viewMode.PLAY);
        vm.put("redPlayer", thisGame.getRedPlayer());
        vm.put("whitePlayer", thisGame.getWhitePlayer());
        vm.put("activeColor", thisGame.getActiveColor());
        vm.put("board", thisGame.getBoardView());

        // render the View
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));

    }

}
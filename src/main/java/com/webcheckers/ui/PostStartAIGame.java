package com.webcheckers.ui;

import com.webcheckers.appl.Game;
import com.webcheckers.model.AI;
import com.webcheckers.model.Color;
import com.webcheckers.model.Player;
import spark.*;

import java.util.logging.Logger;

import static spark.Spark.halt;

public class PostStartAIGame implements Route {


  TemplateEngine templateEngine;
  private static final Logger LOG = Logger.getLogger(PostStartAIGame.class.getName());

    public PostStartAIGame(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;

        LOG.config("PostStartAIGameRoute is initialized.");
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.config("PostStartAIGameRoute is invoked.");

        final Session httpSession = request.session();
        Player red = httpSession.attribute(WebServer.PLAYER_KEY);

        AI ai = new AI();
        Game newGame = new Game(red, ai);
        red.setColor(Color.RED);
        red.startGame(newGame);
        ai.startGame(newGame);

        response.redirect(WebServer.GAME_URL);
        halt();
        return null;

    }
}

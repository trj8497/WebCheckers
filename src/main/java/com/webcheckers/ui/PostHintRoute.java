package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.Game;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import spark.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;
/**
 * The hint route handler
 *
 * @author Jasmine Liang
 * @author Konce Quispe
 */
public class PostHintRoute implements Route {

    //Attributes
    private static final Logger LOG = Logger.getLogger(PostHintRoute.class.getName());
    private final Gson gson;
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    //Constructor

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /hint} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public PostHintRoute(final PlayerLobby lobby, final TemplateEngine templateEngine, Gson gson) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = lobby;
        this.gson = gson;
        LOG.config("GetHintRoute is initialized.");
    }

    /**
     * helper function to get the hint into formatted array
     * @param moves all the valid moves list
     * @return the formatted array list
     */
    private ArrayList getHints(ArrayList<Move> moves){
        ArrayList<Position> arr = new ArrayList<>();
        for(Move move:moves){
            int row = move.getEnd().getRow();
            int col = move.getEnd().getCell();
            arr.add(new Position(row,col));
        }
        return arr;
    }

    //Public methods

    /**
     * Render the WebCheckers Hint feature.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the list translate to json
     */
    @Override
    public Object handle(Request request, Response response) {

        LOG.config("GetHintRoute is invoked.");

        //Get all the attributes from the session first
        final Session httpSession = request.session();
        Player player = httpSession.attribute(WebServer.PLAYER_KEY);
        Color myColor = player.getColor();
        Game thisGame = player.getGame();
        Board board = player.getGame().getBoard();
        ArrayList<Move> moves = new ArrayList<>();

        //Get all the possible jump moves for this player
        if (myColor == Color.WHITE) {
            moves.addAll(board.canWhiteJumpForward(Color.WHITE));
        } else {
            moves.addAll(board.canRedJumpForward(Color.RED));
        }
        //If jump moves were added to the list, don't add anything else
        if (!moves.isEmpty()) {
        }
        //If there are no jump moves, add all possible simple moves for this player
        else {
            if (myColor == Color.WHITE) {
                moves.addAll(board.generateSimple(Color.WHITE));
            } else {
                moves.addAll(board.generateSimple(Color.RED));
            }
        }
        //for all moves in the list, tell the board to show it as a hint

        //the format list with all positions
        ArrayList arr = getHints(moves);

        return gson.toJson(arr);

    }
}

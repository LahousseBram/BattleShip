package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.GameManager;
import be.howest.ti.battleship.web.NotYetImplementedException;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;

public class DeleteGamesRequest extends Request {

    private final GameManager gameManager;

    public DeleteGamesRequest(RoutingContext ctx, GameManager gameManager) {
        super(ctx);
        this.gameManager = gameManager;
    }

    // Response
    @Override
    public void sendResponse() {
        gameManager.getListOfGames().clear(); //remove all games from the list.
        Response.sendJson(ctx, 200, new Object());

        // throw new NotYetImplementedException("delete-games");
    }
}

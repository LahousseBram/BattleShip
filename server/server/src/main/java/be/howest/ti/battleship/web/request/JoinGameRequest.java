package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Game;
import be.howest.ti.battleship.web.NotYetImplementedException;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;

public class JoinGameRequest extends Request {

    private String playerToken;
    private String gameId;

    public JoinGameRequest(RoutingContext ctx) {
        super(ctx);
    }

    // Response
    @Override
    public void sendResponse() {
        Response.sendJson(ctx, 200, Map.of(
                "gameId", gameId,
                "playerToken", playerToken
        ));
    }

    public String getCommanderName() {
        return ctx.body().asJsonObject().getString("commander");
    }

    public Game getGame() {
        return ctx.body().asPojo(Game.class);
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setPlayerToken(String playerToken) {
        this.playerToken = playerToken;
    }
}

package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.*;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.List;

public class FireSalvoRequest extends Request {

    private Game game;
    private List<String> positionStrings;

    public FireSalvoRequest(RoutingContext ctx) {
        super(ctx);
    }

    public void setPositionStrings() {
        this.positionStrings = params.body().getJsonObject().getJsonArray("salvo").getList();
    }

    public String getDefendingCommanderName() {
        return params.pathParameter("defendingCommander").getString();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    // Response
    @Override
    public void sendResponse() {
        Response.sendJson(ctx, 200, game.fireSalvo(positionStrings));
    }
}

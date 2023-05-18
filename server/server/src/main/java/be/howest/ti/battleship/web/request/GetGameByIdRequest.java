package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Game;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.Map;

public class GetGameByIdRequest extends Request {

    private Game game;

    public GetGameByIdRequest(RoutingContext ctx) {
        super(ctx);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    // Response
    @Override
    public void sendResponse() {
        Map<String, Object> out;
        if (game.getDefendingCommander() != null) {
            out = Map.of(
                    "salvoSize", Map.of(
                            game.getAtackingCommander().getName(), game.getAtackingCommander().getSalvoSize(),
                            game.getDefendingCommander().getName(), game.getDefendingCommander().getSalvoSize()
                    ),
                    "sunkShips", Map.of(
                            game.getAtackingCommander().getName(), game.getAtackingCommander().getFleet().getSunkShipTypes(),
                            game.getDefendingCommander().getName(), game.getDefendingCommander().getFleet().getSunkShipTypes()
                    ),
                    "winner", game.getWinner() == null ? false : game.getWinner().getName(),
                    "history", new ArrayList<>(),
                    "attackingCommander", game.getAtackingCommander().getName(),
                    "defendingCommander", game.getDefendingCommander().getName(),
                    "started", game.getStarted(),
                    "id", game.getGameID(),
                    "type", game.getType(),
                    "size", Map.of(
                            "rows", game.getAtackingCommander().getFleet().getRows(),
                            "cols", game.getAtackingCommander().getFleet().getCols()
                    )
            );
        } else {
            out = Map.of(
                    "commanders", new String[]{game.getAtackingCommander().getName()},
                    "started", game.getStarted(),
                    "id", game.getGameID(),
                    "type", game.getType(),
                    "size", Map.of(
                            "rows", game.getAtackingCommander().getFleet().getRows(),
                            "cols", game.getAtackingCommander().getFleet().getCols()
                    )
            );
        }
        Response.sendJson(ctx, 200, out);
    }


}

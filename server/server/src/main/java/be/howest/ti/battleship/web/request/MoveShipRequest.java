package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Game;
import be.howest.ti.battleship.logic.Position;
import be.howest.ti.battleship.logic.Ship;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;

public class MoveShipRequest extends Request {
    private Ship ship;

    public MoveShipRequest(RoutingContext ctx) {
        super(ctx);
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip(Game game) {
        String shipName = ctx.pathParam("ship");
        return game.getAtackingCommander().getFleet().getShipByName(shipName);
    }

    // Response
    @Override
    public void sendResponse() {
        Response.sendJson(ctx, 200, Map.of(
                "ship", ship.getShipType(),
                "location", ship.getShipPositions().stream().map(Position::toString).toArray()
        ));
    }
}

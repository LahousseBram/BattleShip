package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Commander;
import be.howest.ti.battleship.logic.Fleet;
import be.howest.ti.battleship.logic.Game;
import be.howest.ti.battleship.logic.GameManager;
import be.howest.ti.battleship.web.BattleshipUser;
import be.howest.ti.battleship.web.NotYetImplementedException;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetFleetDetailsRequest extends Request {

    private Fleet fleet;

    public GetFleetDetailsRequest(RoutingContext ctx) {
        super(ctx);
    }

    public String getGameID() {
        return params.queryParameter("gameId").getString();
    }

    public String getGameCommander() {
        return params.queryParameter("commander").getString();
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    // Response
    @Override
    public void sendResponse() {
        Map<String, Fleet> output = new HashMap<>();
        output.put("Fleet", this.fleet);
        ctx.json(output);
    }
}

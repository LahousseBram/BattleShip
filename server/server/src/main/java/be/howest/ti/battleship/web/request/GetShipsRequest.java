package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.ShipType;
import be.howest.ti.battleship.web.NotYetImplementedException;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;

public class GetShipsRequest extends Request {
    private ShipType[] shipTypes;
    public GetShipsRequest(RoutingContext ctx) {
        super(ctx);
    }

    public void setShipTypes(ShipType[] shipTypes) {
        this.shipTypes = shipTypes;
    }

    // Response
    @Override
    public void sendResponse() {
        Map<String, ShipType[]> out = new HashMap<>();
        out.put("ships", this.shipTypes);
        ctx.json(out);
    }

}

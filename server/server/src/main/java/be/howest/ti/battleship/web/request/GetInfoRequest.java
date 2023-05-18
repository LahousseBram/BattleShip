package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.web.NotYetImplementedException;
import io.vertx.ext.web.RoutingContext;

public class GetInfoRequest extends Request {
    public GetInfoRequest(RoutingContext ctx) {
        super(ctx);
    }

    // Response
    @Override
    public void sendResponse() {
        throw new NotYetImplementedException("get-info");
    }
}

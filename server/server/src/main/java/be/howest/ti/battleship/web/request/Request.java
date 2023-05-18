package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.web.BattleshipUser;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;


public abstract class Request {

    final RoutingContext ctx;
    final RequestParameters params;

    Request(RoutingContext ctx) {
        this.ctx = ctx;
        this.params = ctx.get(ValidationHandler.REQUEST_CONTEXT_KEY);
    }

    public BattleshipUser getUser() {
        return (BattleshipUser) ctx.user();
    }

    public String getGameId() {
        return ctx.pathParam("gameId");
    }

    public abstract void sendResponse();


}

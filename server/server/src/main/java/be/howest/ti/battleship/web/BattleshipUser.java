package be.howest.ti.battleship.web;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.impl.UserImpl;

public class BattleshipUser extends UserImpl {

    public  BattleshipUser(String gameId, String commander) {
        super(
                new JsonObject().put("gameId", gameId).put("commander", commander),
                new JsonObject()
        );
    }

    public String getGameId() {
        return super.principal().getString("gameId");
    }

    public String getCommander() {
        return super.principal().getString("commander");
    }
}

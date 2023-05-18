package be.howest.ti.battleship.web.tokens;

import be.howest.ti.battleship.web.BattleshipUser;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.auth.authentication.TokenCredentials;

public interface TokenManager extends AuthenticationProvider {

    String createToken(User user);

    BattleshipUser createUser(String token);


    @Override
    default void authenticate(JsonObject credentials, Handler<AsyncResult<User>> handler) {
        TokenCredentials tokenCredentials = credentials.mapTo(TokenCredentials.class);
        String token = tokenCredentials.getToken();

        try {
            handler.handle(Future.succeededFuture(
                    this.createUser(token)
            ));
        } catch (InvalidTokenException ex) {
            handler.handle(Future.failedFuture(ex));
        }
    }

    boolean isValidPlayerName(String playerName);
}

package be.howest.ti.battleship.web.tokens;

import be.howest.ti.battleship.web.BattleshipUser;
import io.vertx.ext.auth.User;

public class PlainTextTokens implements TokenManager {

    private static final int TOKEN_GAME_ID_PART = 0;
    private static final int TOKEN_COMMANDER_PART = 1;
    private static final int TOKEN_EXPECTED_PARTS = 2;

    @Override
    public String createToken(User user) {
        return String.format("%s-%s", user.get("gameId"), user.get("commander"));
    }

    @Override
    public BattleshipUser createUser(String token) {
        String[] parts = token.split("-");

        if (parts.length != TOKEN_EXPECTED_PARTS) {
            throw new InvalidTokenException();
        }

        return new BattleshipUser(parts[TOKEN_GAME_ID_PART], parts[TOKEN_COMMANDER_PART]);
    }

    @Override
    public boolean isValidPlayerName(String playerName) {
        return !playerName.contains("-");
    }

}

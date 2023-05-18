package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Game;
import be.howest.ti.battleship.logic.GameManager;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GetGamesRequest extends Request {

    private String prefix;
    private int status; // 0 = null, 1 = waiting, 2 = started
    private int cols;
    private int rows;
    private String type;

    private GameManager gameManager;

    private List<Game> games;

    public GetGamesRequest(RoutingContext ctx) {
        super(ctx);
        this.gameManager = null;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setGames(List<Game> games){
        this.games = games;
    }

    public int filterStatus(String status) {
        if (status == null) return 0;
        if (status.equalsIgnoreCase("waiting")) return 1;

        return 2;
    }

    public void initializeValues() {
        if (params.queryParameter("type") != null) {
            this.type = params.queryParameter("type").getString();
        }
        if (params.queryParameter("status") != null) {
            this.status = filterStatus(params.queryParameter("status").getString());
        }
        if (params.queryParameter("cols") != null) {
            this.cols = params.queryParameter("cols").getInteger();
        }
        if (params.queryParameter("rows") != null) {
            this.rows = params.queryParameter("rows").getInteger();
        }
    }

    public List<Game> getGames() {
        List<Game> res;
        initializeValues();

        res = gameManager.filterGamesByType(
                gameManager.filterGamesByStatus(
                        gameManager.filterGamesByRow(
                                gameManager.filterGamesByCol(games, this.cols), this.rows), this.status), this.type);

        return res;
    }

    // Response
    @Override
    public void sendResponse() {
        Response.sendJson(ctx, 200, Map.of(
                "games", getGames()
        ));
    }
}

package be.howest.ti.battleship.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private Commander atackingCommander;
    private Commander defendingCommander;
    private final String prefix;
    private String gameID;
    private final String type;
    //TODO: make turn class
    //private List<Turn> history;
    private boolean started;

    private Commander winner;

    @JsonCreator
    public Game(
            @JsonProperty("commander") String commanderName,
            @JsonProperty("type") String type,
            @JsonProperty("fleet") Fleet fleet,
            @JsonProperty("prefix") String prefix
    ) {
        this.prefix = prefix;
        this.type = type;
        atackingCommander = new Commander(fleet, commanderName);
        atackingCommander.setGame(this);
        winner = null;
        started = false;
    }

    public void nextTurn() {
        Commander temp = this.atackingCommander;
        this.atackingCommander = this.defendingCommander;
        this.defendingCommander = temp;
    }

    public Commander getWinner() {
        return winner;
    }

    public Commander getAtackingCommander() {
        return atackingCommander;
    }

    public Commander getDefendingCommander() {
        return defendingCommander;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getType() {
        return type;
    }

    public boolean getStarted() {
        return started;
    }

    public String getGameID() {
        return gameID;
    }

    //    public List<Turn> getHistory() {
//        return history;
//    }

    public void start() {
        if (!started) {
            started = true;
        }
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public Map<String, Object> fireSalvo(List<String> salvo) {
        Map<String, Object> salvoResult = new HashMap<>();
        for (String strPos : salvo) {
            Position pos = new Position(strPos);
            Ship ship = defendingCommander.getFleet().isHit(pos);
            salvoResult.put(strPos, ship == null ? false : ship.toString());
        }
        if (defendingCommander.getFleet().getShips().isEmpty()) {
            winner = atackingCommander;
        }
        nextTurn();
        return salvoResult;
    }

    public void join(Commander commander) {
        start();
        defendingCommander = commander;
        defendingCommander.setGame(this);
    }
}

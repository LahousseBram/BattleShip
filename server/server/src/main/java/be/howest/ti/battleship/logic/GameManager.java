package be.howest.ti.battleship.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

    private final Map<String, Game> listOfGames;

    public GameManager() {
        this.listOfGames = new HashMap<>();
    }

    public List<Game> filterGamesByType(List<Game> games, String type) {
        if (type == null) return games;
        List<Game> filteredGames = new ArrayList<>();

        for (Game game : games) {
            if (game.getType().equalsIgnoreCase(type)) filteredGames.add(game);
        }

        return filteredGames;
    }

    public List<Game> filterGamesByStatus(List<Game> games, int status) {
        if (status == 0) return games;
        List<Game> filteredGames = new ArrayList<>();

        for (Game game : games) {
            if (!game.getStarted() && status == 1) filteredGames.add(game);
            if (game.getStarted() && status == 2) filteredGames.add(game);
        }

        return filteredGames;
    }

    public List<Game> filterGamesNotStarted(List<Game> games) {
        List<Game> filteredGames = new ArrayList<>();
        for (Game game : games) {
            if (!game.getStarted()) filteredGames.add(game);
        }
        return filteredGames;
    }

    public List<Game> filterGamesByRow(List<Game> games, int row) {
        if (row == 0) return games;
        List<Game> filteredGames = new ArrayList<>();

        for (Game game : games) {
            if (game.getAtackingCommander().getFleet().getRows() == row) filteredGames.add(game);
        }

        return filteredGames;
    }

    public List<Game> filterGamesByCol(List<Game> games, int col) {
        if (col == 0) return games;
        List<Game> filteredGames = new ArrayList<>();

        for (Game game : games) {
            if (game.getAtackingCommander().getFleet().getCols() == col) filteredGames.add(game);
        }

        return filteredGames;
    }

    public Map<String, Game> getListOfGames() {
        return this.listOfGames;
    }

    public String generateGameID() {
        return generateGameID("");
    }

    public void addGame(Game game) {
        String gameID = generateGameID(game.getPrefix());
        game.setGameID(gameID);
        listOfGames.put(gameID, game);
    }

    public String generateGameID(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return Integer.toString(this.listOfGames.size() + 1);
        } else {
            return (prefix + "_") + (this.listOfGames.size() + 1);
        }
    }

    public Commander getCommanderByName(String gameID, String commanderName) {
        Game currentGame = getGameByID(gameID);
        if (currentGame == null) return null;

        return currentGame.getAtackingCommander().getName().equalsIgnoreCase(commanderName) ? currentGame.getAtackingCommander() : currentGame.getDefendingCommander();
    }

    public Game getGameByID(String gameID) {
        return listOfGames.getOrDefault(gameID, null);
    }

    public Game matchGame(Game game) {
        List<Game> filteredGames = filterGamesByType(
                filterGamesNotStarted(
                        filterGamesByRow(
                                filterGamesByCol(
                                        List.copyOf(listOfGames.values()), game.getAtackingCommander().getFleet().getCols()
                                ), game.getAtackingCommander().getFleet().getRows())
                ), game.getType());
        for (Game matchedGame: filteredGames) {
            if (!matchedGame.getAtackingCommander().getName().equals(game.getAtackingCommander().getName())) return matchedGame;
        }
        return null;
    }
}

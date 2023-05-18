package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GetGamesRequestTest {

    public List<Game> getGames(List<Game> games, int cols, int rows, int status, String type) {
        List<Game> res;

        GameManager gameManager = new GameManager();

        res = gameManager.filterGamesByType(
                gameManager.filterGamesByStatus(
                        gameManager.filterGamesByRow(
                                gameManager.filterGamesByCol(games, cols), rows), status), type);
        System.out.println(res);

        return res;
    }

    private List<Game> games;

    private void setUp() {
        initializeGameData();
    }

    private void initializeGameData() {
        games = new ArrayList<>();
        HashSet<Fleet> hashSetFleets = new HashSet<>();

        List<Position> shipPositions = new ArrayList<>();
        shipPositions.add(new Position(2, 3));
        shipPositions.add(new Position(3, 3));
        shipPositions.add(new Position(4, 3));
        shipPositions.add(new Position(5, 3));

        hashSetFleets.add(new Fleet(1, 4, Set.of(new Ship(new ShipType(1, "carrier"), shipPositions))));

        games.add(new Game("Alice", "salvo", new Fleet(1, 1, new HashSet<>()), "test"));
        games.add(new Game("Carol", "move", new Fleet(20, 20, new HashSet<>()), "test"));
        games.add(new Game("John", "regular", new Fleet(10, 10, new HashSet<>()), "test"));
        games.add(new Game("Bernard", "salvo", new Fleet(10, 10, new HashSet<>()), "test"));

        GameManager gameManager = new GameManager();
        for (Game game : games) {
            gameManager.addGame(game);
        }
    }

    @Test
    @DisplayName("Test filtering on all games")
    void filterOnAllGames() {
        setUp();
        assertEquals(4, getGames(games, 0, 0, 0, null).size());
    }

    @Test
    @DisplayName("Test filtering specific game-mode types")
    void filterOnSalvoGames() {
        setUp();
        assertEquals(2, getGames(games, 0, 0, 0, "salvo").size());
    }

    @Test
    @DisplayName("Test filtering on a specific col en row size")
    void filterBoardSizes() {
        setUp();
        assertEquals(1, getGames(games, 20, 20, 0, null).size());
    }

    @Test
    @DisplayName("Test filtering on started games")
    void filterOnStartedGames() {
        setUp();
        assertEquals(4, getGames(games, 0, 0, 1, null).size());
    }
}

package be.howest.ti.battleship.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    @Test
    void testCols() {
        List<Game> games = new ArrayList<>();
        HashSet<Fleet> hashSetFleets = new HashSet<>();

        List<Position> shipPositions = new ArrayList<>();
        shipPositions.add(new Position(2, 3));
        shipPositions.add(new Position(3, 3));
        shipPositions.add(new Position(4, 3));
        shipPositions.add(new Position(5, 3));

        hashSetFleets.add(new Fleet(1, 4, Set.of(new Ship(new ShipType(1, "carrier"), shipPositions))));

        games.add(new Game("1", "1", new Fleet(1, 7, new HashSet<>()), "test"));
        games.add(new Game("1", "1", new Fleet(1, 1, new HashSet<>()), "test"));
        games.add(new Game("1", "1", new Fleet(3, 7, new HashSet<>()), "test"));

        assertEquals(new GameManager().filterGamesByCol(games, 7).size(), 2);

    }

}
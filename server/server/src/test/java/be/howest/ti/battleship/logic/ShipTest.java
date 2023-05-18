package be.howest.ti.battleship.logic;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    void move() {
        Ship ship = new Ship("destroyer", new String[]{"C-2", "C-3"});
        ship.move();
        assertEquals(List.of(new Position("C-3"), new Position("C-4")), ship.getShipPositions());
    }
}
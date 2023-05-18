package be.howest.ti.battleship.logic;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FleetTest {

    @Test
    void isHit() {
        Set<Ship> shipSet = Set.of(new Ship("Carrier", new String[]{"A-1", "B-1"}));
        Fleet fleet = new Fleet(10, 10, shipSet);
        //Ship ship = fleet.handleShipHit(new Position("A-1"));

        assertEquals(fleet.isHit(new Position("A-1")), new Ship("Carrier", new String[]{"A-1", "B-1"}));
    }

    @Test
    void handleShipHit() {
        List<Ship> shipSet = List.of(new Ship("Carrier", new String[]{"A-1", "B-1"}));
        Fleet fleet = new Fleet(10, 10, new HashSet<>(shipSet));
        //Ship ship = fleet.handleShipHit(new Position("A-1"));

        assertEquals(shipSet.get(0).getShipPositions().get(0), new Position("A-1"));
        //assertEquals(true, new Ship("Carrier", new String[]{"A-1", "B-1"}).positionInShip(new Position("A-1")));
        //assertEquals(new Ship("Carrier", new String[]{"A-1", "B-1"}), fleet.isHit(new Position("A-1")));
    }
}
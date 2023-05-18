package be.howest.ti.battleship.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.HashSet;
import java.util.Set;

public class Fleet {
    private final int rows;
    private final int cols;
    private final Set<Ship> ships;
    private final Set<Ship> sunkShips;

    @JsonCreator
    public Fleet(
            @JsonProperty("rows") int rows,
            @JsonProperty("cols") int cols,
            @JsonProperty("ships") Set<Ship> ships
    ) {
        this.rows = rows;
        this.cols = cols;
        this.ships = ships;
        this.sunkShips = new HashSet<>();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public Set<Ship> getSunkShips() {
        return sunkShips;
    }

    public Set<ShipType> getSunkShipTypes() {
        Set<ShipType> types = new HashSet<>();
        for (Ship ship: getSunkShips()) {
            types.add(ship.getShipType());
        }
        return types;
    }

    public boolean place(Ship ship) {
        if (ships.contains(ship)) {
            return false;
        } else {
            ships.add(ship);
            return true;
        }
    }

    public Ship isHit(Position p) {
        for (Ship ship : ships) {
            if (ship.positionInShip(p)) {
                ship.addSunkPosition(p);
                if (ship.getShipType().getSize() == ship.getSunkPositions().size()) {
                    this.sunkShips.add(ship);
                    this.ships.remove(ship);
                }
                return ship;
            }
        }
        return null;
    }

    public Ship getShipByName(String name) {
        for (Ship ship: ships) {
            if (ship.getShipType().getName().equals(name)) {
                return ship;
            }
        }
        return null;
    }
}

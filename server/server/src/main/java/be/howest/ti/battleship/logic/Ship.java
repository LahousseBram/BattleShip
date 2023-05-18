package be.howest.ti.battleship.logic;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private ShipType shipType;
    private final List<Position> shipPositions;
    private final List<Position> sunkPositions;

    public Ship(ShipType shipType, List<Position> shipPositions) {
        this.shipType = shipType;
        this.shipPositions = shipPositions;
        this.sunkPositions = new ArrayList<>();
    }

    @JsonCreator
    public Ship(
            @JsonProperty("name") String name,
            @JsonProperty("location") String[] shipPositions
    ) {
        this.shipType = null;
        for (ShipType type: ShipType.getDefaultShipTypes()) {
            if (type.getName().equals(name)) {
                this.shipType = type;
                break;
            }
        }
        this.shipPositions = new ArrayList<>();
        for (String strPos: shipPositions) {
            this.shipPositions.add(new Position(strPos));
        }
        this.sunkPositions = new ArrayList<>();
    }

    public List<Position> getShipPositions() {
        return shipPositions;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public List<Position> getSunkPositions() {
        return sunkPositions;
    }

    public int getLength() {
        return shipPositions.size();
    }

    public void addSunkPosition(Position position) {
        this.sunkPositions.add(position);
    }

    public boolean positionInShip(Position p) {
        for (Position position : shipPositions) {
            if (position.equals(p)) return true;
        }

        return false;
    }

    public void move() {
        int row = shipPositions.get(shipPositions.size() - 1).getRow();
        int col = shipPositions.get(shipPositions.size() - 1).getCol();

        if (row > shipPositions.get(0).getRow()) {
            row++;
        } else if (row < shipPositions.get(0).getRow()) {
            row--;
        } else if (col > shipPositions.get(0).getCol()) {
            col++;
        } else {
            col--;
        }
        shipPositions.add(new Position(row, col));
        shipPositions.remove(0);
    }


}

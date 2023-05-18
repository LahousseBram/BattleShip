package be.howest.ti.battleship.logic;

import java.util.Objects;

public class ShipType {
    private final int size;
    private final String name;

    public ShipType(int size, String name) {
        this.size = size;
        this.name = name;
    }
    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public static ShipType[] getDefaultShipTypes() {
        return new ShipType[] {
                new ShipType(5, "carrier"),
                new ShipType(4, "battleship"),
                new ShipType(3, "cruiser"),
                new ShipType(3, "submarine"),
                new ShipType(2, "destroyer")
            };
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShipType shipType = (ShipType) o;
        return size == shipType.size && Objects.equals(name, shipType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, name);
    }
}

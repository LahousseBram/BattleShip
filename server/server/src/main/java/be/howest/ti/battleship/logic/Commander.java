package be.howest.ti.battleship.logic;

public class Commander {
    private Fleet fleet;
    private String name;
    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    public Commander(Fleet fleet, String name) {
        this.fleet = fleet;
        this.name = name;
    }

    public int getSalvoSize() {
        if (game.getType().equals("salvo")) {
            return this.fleet.getShips().size();
        }
        return 1;
    }

    public Fleet getFleet() {
        return fleet;
    }

    public String getName() {
        return name;
    }
}

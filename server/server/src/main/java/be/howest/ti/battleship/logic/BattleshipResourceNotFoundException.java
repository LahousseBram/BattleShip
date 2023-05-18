package be.howest.ti.battleship.logic;


public class BattleshipResourceNotFoundException extends BattleshipException {
    public BattleshipResourceNotFoundException(String gameId) {
        super("Game " + gameId + " not found");
    }
}

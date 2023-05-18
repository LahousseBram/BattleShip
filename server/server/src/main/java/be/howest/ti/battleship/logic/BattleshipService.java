package be.howest.ti.battleship.logic;

import java.util.List;

public interface BattleshipService {

    String getVersion();

    List<Person> sortByName(List<Person> ppl);
    List<Person> sortByAge(List<Person> ppl);

    GameManager getGameManager();
    
}

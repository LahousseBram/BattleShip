package be.howest.ti.battleship.logic;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleshipServiceImpl implements BattleshipService {

    private final GameManager gameManager;

    public BattleshipServiceImpl() {
        this.gameManager = new GameManager();
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public List<Person> sortByName(List<Person> ppl) {
        List<Person> copy = new ArrayList<>(ppl);
        Collections.sort(copy);
        return copy;
    }

    @Override
    public List<Person> sortByAge(List<Person> ppl) {
        List<Person> copy = new ArrayList<>(ppl);
        Collections.sort(copy, new ByAge());
        return copy;
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }

}

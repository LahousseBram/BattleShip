package be.howest.ti.battleship.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleshipServiceTest {

    @Test
    void getVersion() {
        BattleshipService service = new BattleshipServiceImpl();
        assertEquals("0.0.1", service.getVersion());
    }
}

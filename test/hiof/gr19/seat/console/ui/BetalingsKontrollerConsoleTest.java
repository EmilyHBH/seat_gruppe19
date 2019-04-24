package hiof.gr19.seat.console.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BetalingsKontrollerConsoleTest extends ConsoleTest {

    BetalingsKontrollorConsole bc;

    @BeforeEach
    public void setup(){
        bc = new BetalingsKontrollorConsole();
    }

    @Test
    public void checkTicketIsValid(){

        // Krav 025

        // ticket 1 med amound 1 finnes i db som dummy data
        assertTrue(bc.isTicketIdValid(1,1, 1));
        assertTrue(!bc.isTicketIdValid(1512487365,10,1));



    }

}

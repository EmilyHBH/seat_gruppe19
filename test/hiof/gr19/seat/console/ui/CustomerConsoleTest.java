package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.model.Arrangement;
import hiof.gr19.seat.Database;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CustomerConsoleTest extends ConsoleTest {

    CustomerConsole cc;

    @BeforeEach
    public void setup(){
        cc = new CustomerConsole();
    }



    @Test
    public void testValgAvArrangement() throws SQLException, ClassNotFoundException, IOException {

        Database db = new Database();
        ArrayList<Arrangement> events = db.getEvents();

        provideInput("2");
        cc.selectEvent(events);

    }

    @Test
    public void testVippsBetaling(){
        //Krav 014

        String navn = "Test TestTestesen";
        int betalingsMetode = 3; //Vipps = 3

        String telefonnummer = "12345678";

        provideInput(telefonnummer);

        //params: navn på bruker, betalings metode
        cc.selectPaymentMethod(betalingsMetode);
        assertEquals("Selected: Vipps\n" +
                "\n" +
                "Ditt telefonnummer:\n" +
                "\n", getConsoleOutput());
    }

    @Test
    public void testKortBetaling(){
        //Krav 012

        String navn = "Test Testesen";
        int betalingsMetode = 1; //Kort = 1

        cc.selectPaymentMethod(betalingsMetode);
        //"bankkort\n" = sout("bankort")
        assertEquals("Selected: Card\n", getConsoleOutput());
    }

    @Test
    public void testKontantBetaling(){
        //Krav 013

        String navn = "Test Testesen";
        int betalingsMetode = 2; //Kontant = 2

        cc.selectPaymentMethod(betalingsMetode);
        assertEquals(getConsoleOutput(), "Selected: Cash\n");
    }

    @Test
    public void testVelgAntallBilletter(){
        //Krav 021

        int antallBilletter = 21;

        provideInput(String.valueOf(antallBilletter));

        assertEquals(antallBilletter, cc.selectTicketAmount());
    }
}
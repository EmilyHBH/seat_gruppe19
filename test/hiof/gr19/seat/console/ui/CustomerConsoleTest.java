package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.model.Arrangement;
import hiof.gr19.seat.Database;
import hiof.gr19.seat.model.Organizer;
import hiof.gr19.seat.model.Purchase;
import hiof.gr19.seat.model.Ticket;
import hiof.gr19.seat.stubs.confirmation.ConfirmationMethod;
import hiof.gr19.seat.stubs.confirmation.EmailReciept;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CustomerConsoleTest extends ConsoleTest {

    CustomerConsole cc;

    @BeforeEach
    public void setup(){
        cc = new CustomerConsole();
    }



    @Test
    public void tilbyArrangementer(){
        //Krav 011
        ArrayList<Arrangement> arrangs;

        try {
            arrangs = cc.getArrangements();
            cc.showEvents(arrangs);
        }catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }

        //Replace with REGEX EXpression.
        assertTrue(getConsoleOutput()!= null);
    }

    @Test
    public void testValgAvArrangement() throws SQLException, ClassNotFoundException, IOException {
        //Krav 013

        Database db = new Database();
        ArrayList<Arrangement> events = db.getEvents();

        provideInput("0");
        cc.selectEvent(events);

    }

    @Test
    public void testVippsBetaling(){
        //Krav 017

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
        //Krav 015

        String navn = "Test Testesen";
        int betalingsMetode = 1; //Kort = 1

        cc.selectPaymentMethod(betalingsMetode);
        //"bankkort\n" = sout("bankort")
        assertEquals("Selected: Card\n", getConsoleOutput());
    }

    @Test
    public void testKontantBetaling(){
        //Krav 016

        String navn = "Test Testesen";
        int betalingsMetode = 2; //Kontant = 2

        cc.selectPaymentMethod(betalingsMetode);
        assertEquals(getConsoleOutput(), "Selected: Cash\n");
    }

    @Test
    public void emailConfirmationTest(){
        //Krav 018
        String email = "Test@Testesen.no";

        //De fleste objekter er tomme fordi det vi tester
        //Er outputen fra sendConfirmation metoden

        Arrangement testArrangement = null;
        Purchase testPurchase = new Purchase(testArrangement);
        ConfirmationMethod confirmationMethod = new EmailReciept(email);
        testPurchase.setConfirmationMethod(confirmationMethod);
        confirmationMethod.sendConfirmation(testPurchase);
        assertEquals(getConsoleOutput(),"Purchase confirmation sent to: " + email + "\n");

    }

    @Test
    public void testVelgAntallBilletter(){
        //Krav 024

        String antallBilletter = "21";
        int IDOfTicketWeWantToBuy = -1;

        Arrangement arrangement = new Arrangement(-1, "test arrangement", "test", new Date(), new Organizer(-1,"testorg", "testorg@testmail.org"), 200, "",
                new ArrayList<>(){{
                    new Ticket(-1, 200, 50, "billett 1");
                    new Ticket(-2, 250, 150, "billett 2");
                }});

        provideInput(antallBilletter);

        assertEquals(antallBilletter, cc.selectTicketAmount(IDOfTicketWeWantToBuy, arrangement));
    }
}
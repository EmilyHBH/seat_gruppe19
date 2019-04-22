package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Arrangement;
import hiof.gr19.seat.Database;
import hiof.gr19.seat.stubs.BetalingsStub;
import hiof.gr19.seat.stubs.emailReciept;
import hiof.gr19.seat.stubs.printReciept;
import org.junit.jupiter.api.*;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CustomerConsoleTest {
    //For å simulere bruker input
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayInputStream forcedInput;
    private ByteArrayOutputStream testOut;

    CustomerConsole cc;

    @BeforeEach
    public void setup(){
        cc = new CustomerConsole();
    }

    @Test
    public void testValgAvArrangement() throws SQLException, ClassNotFoundException, IOException {

        Database db = new Database();
        ArrayList<Arrangement> events = db.getEvents();

        provideInput("0");
        Arrangement chosen = cc.chooseEvent(events);

        // Uses the predefined dummy data to check if correct
        assertEquals(chosen.getArrangmentTitle(), "Fyre Festival");
    }

    @Test
    public void testKortBetaling(){
        //Krav 012

        provideInput("Navn mcNavn\n1");
        cc.establishPaymentMethod();

        assertTrue(cc.betalingsStub != null);
    }

    @Test
    public void testKontantBetaling(){
        //Krav 013

        provideInput("Ny McNY\n2");
        cc.establishPaymentMethod();

        assertTrue(cc.betalingsStub != null);
    }

    @Test
    public void testVippsBetaling(){
        //Krav 014

        provideInput("Test Testesen\n3\n12345678");
        cc.establishPaymentMethod();

        assertTrue(cc.betalingsStub != null);
    }

    @Test
    public void testEpostSomBevis(){
        // Krav 015

        provideInput("1\ne@e.e");
        cc.establishConfirmationMethod();

        assertTrue(cc.confirmationMethod instanceof emailReciept);
    }

    @Test
    public void testPrintSomBevis(){
        // Krav 015

        provideInput("2");
        cc.establishConfirmationMethod();

        assertTrue(cc.confirmationMethod instanceof printReciept);
    }

    private void provideInput(String data) {
        forcedInput = new ByteArrayInputStream(data.getBytes());
        System.setIn(forcedInput);
    }

    private String getConsoleOutput(){
        return testOut.toString();
    }

    @BeforeEach
    public void setupOutput(){
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    public void restoreSystemInput(){
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

}
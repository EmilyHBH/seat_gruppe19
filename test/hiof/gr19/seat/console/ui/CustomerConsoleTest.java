package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.model.Arrangement;
import hiof.gr19.seat.Database;
import hiof.gr19.seat.model.Organizer;
import hiof.gr19.seat.model.Purchase;
import hiof.gr19.seat.model.Ticket;
import hiof.gr19.seat.stubs.confirmation.ConfirmationMethod;
import hiof.gr19.seat.stubs.confirmation.EmailReciept;
import hiof.gr19.seat.stubs.confirmation.PrintReciept;
import hiof.gr19.seat.stubs.payment.PayWithCard;
import hiof.gr19.seat.stubs.payment.PayWithCash;
import hiof.gr19.seat.stubs.payment.PayWithVipps;
import hiof.gr19.seat.stubs.payment.PaymentMethod;
import org.junit.jupiter.api.AfterEach;
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
        Arrangement valgtArrangement = cc.selectEvent(events);

        assertNotNull(valgtArrangement);

    }

    @Test
    public void testVippsBetaling(){
        //Krav 017

        int betalingsMetode = 3; //Vipps = 3

        String telefonnummer = "12345678";

        provideInput(telefonnummer);

        //params: navn på bruker, betalings metode
        PaymentMethod paymentMethod = cc.selectPaymentMethod(betalingsMetode);
        assertTrue(paymentMethod instanceof PayWithVipps);
    }

    @Test
    public void testKortBetaling(){
        //Krav 015

        int betalingsMetode = 1; //Kort = 1

        PaymentMethod paymentMethod = cc.selectPaymentMethod(betalingsMetode);
        //"bankkort\n" = sout("bankort")
        assertTrue(paymentMethod instanceof PayWithCard);
    }

    @Test
    public void testKontantBetaling(){
        //Krav 016

        int betalingsMetode = 2; //Kontant = 2

        PaymentMethod paymentMethod = cc.selectPaymentMethod(betalingsMetode);
        assertTrue(paymentMethod instanceof PayWithCash);
    }

    @Test
    public void emailConfirmationTest(){
        //Krav 018

        int confirmationMethodChoice = 1;   // Email

        provideInput("email@email.email");

        ConfirmationMethod confirmationMethod = cc.selectConfirmationMethod(confirmationMethodChoice);
        assertTrue(confirmationMethod instanceof EmailReciept);

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


    @Test
    public void printTicketTest(){
        //Krav 015

        assertTrue(cc.selectConfirmationMethod(2).getClass().isInstance(new PrintReciept()));

    }

    @Test
    public void tilgjengeligBiletter(){

        //Krav 019

        Arrangement testArrangement = null;
        Database db = new Database();
        try {
            testArrangement = db.getEventById(1);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        assertNotEquals(0,testArrangement.getAvailableTickets());

    }





}
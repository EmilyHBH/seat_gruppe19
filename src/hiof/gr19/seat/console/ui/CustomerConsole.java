package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Arrangement;
import hiof.gr19.seat.BetalingsStub;
import hiof.gr19.seat.Database;
import hiof.gr19.seat.Ticket;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerConsole extends Console{

    @Override
    public void start() {

        super.start();

        /*Person user;

        if(askBooleanQuestionAndReturnAnswer("Do you already have an account"))
            user = customerLogin();
        else {
            if(askBooleanQuestionAndReturnAnswer("Do you want to create an account")){
                user = registerCustomer();

                // input new organizer to db
                try {
                    db.addOrganizer(user);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }*/

        customerMenu();
    }

    private static void customerMenu(){

        // Prints all events
        try {
            ArrayList<Arrangement> allEvents = db.getEvents();
            printArrangements(allEvents);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Start purchaseticketmenu on seleted event
        String arrangmentID = console.readLine(">");
        try {
            purchaseTicketMenu(db.getEventById(Integer.parseInt(arrangmentID)));
        } catch (IOException e) {
            System.out.println(e);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    private static void purchaseTicketMenu(Arrangement arrangement) throws IOException {

        // Print all the arrangements different types of tickets
        for(Ticket ticket : arrangement.getAvailableTickets())
            System.out.println(ticket.getId() + "\t" + ticket.getBeskrivelse() + "\t" + ticket.getAntall());

        int ticketId = validateIntInput("id of ticket you want to buy");
        int ticketAmount = validateIntInput("How many tickets");


        if (ticketAmount > arrangement.getMaxAttendees()){
            throw new IOException("No more tickets left");

        }else {
            purchaseTicket(ticketId, ticketAmount);
        }

        // TODO:: Update the arrangement class instance

        // TODO:: db registration of purchased tickets

    }

    public static void purchaseTicket(int ticketID, int ticketAmount) {

        System.out.println("Type name: ");
        String name = console.readLine(">");

        System.out.println("and confirmation method:");
        System.out.println("1 = Epost");
        System.out.println("2 = Print");
        int alternativ2 = Integer.parseInt(console.readLine(">"));

        switch (alternativ2) {
            case 1:
                System.out.println("Epost");
                String epost = console.readLine(">");
                break;
            case 2:
                System.out.println("Print");
                break;
        }

        System.out.println("Velg betalingsmåte: ");
        System.out.println("1 = Bankkort");
        System.out.println("2 = Kontanter");
        System.out.println("3 = Vipps" );
        int alternativ3 = Integer.parseInt(console.readLine(">"));

        BetalingsStub betaling;
        betaling = null;

        switch (alternativ3) {
            case 1:
                System.out.println("Bankkort");
                betaling = new BetalingsStub(name);
                break;

            case 2:
                System.out.println("Kontanter");
                betaling = new BetalingsStub(name);
                break;
            case 3:
                System.out.println("Vipps");
                System.out.println("Ditt telefonnummer: ");
                int telefonnummer = Integer.parseInt(console.readLine(">"));
                betaling = new BetalingsStub(telefonnummer, name);
                break;
            default:
                break;
        }

        boolean betalingGodkent = betaling.godkjentBetaling();

        if (betalingGodkent) {
            try {
                db.ticketsHaveBeenPurchasedFromEvent(ticketID, ticketAmount);
            }catch (SQLException | ClassNotFoundException ex){
                ex.printStackTrace();
            }
        }
        System.out.println("Betalingen var: " + betalingGodkent);
    }

}

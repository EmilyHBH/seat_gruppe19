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

    private void customerMenu(){

        ArrayList<String> menuListOfFunctions = new ArrayList<>(){{
            add("View events");
            add("Exit");
        }};

        int menuOptionChosen = selectFromList(menuListOfFunctions);

        switch(menuOptionChosen){
            case 1:                                 // View events
                showEvents();
                break;
            case 2:
                finish();                           // Exits the program
                break;
            default:
                System.out.println("That was not a valid option");
                break;
        }

        // Recursive call so that the program will return to main menu instead of shut down
        customerMenu();

    }

    private void showEvents(){

        // Prints all events
        try {
            ArrayList<Arrangement> events = db.getEvents();
            printArrangements(events);

            // Start purchaseticketmenu on seleted event
            int arrangmentID = -1;

            while(arrangmentID < 0 || arrangmentID > events.size() -1)
                arrangmentID = validateIntInput("Which event do you want to buy ticket for? answer by using the id");

            purchaseTicketMenu(db.getEventById(events.get(arrangmentID).getArrangementID()));

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void purchaseTicketMenu(Arrangement arrangement) throws IOException {

        System.out.println("\nThe tickets this event offers:\n");

        ArrayList<Ticket> tickets = arrangement.getAvailableTickets();

        if(tickets.size() > 0) {

            // Print all the arrangements different types of tickets
            for (Ticket ticket : tickets)
                System.out.println(ticket.getId() + "\t" + ticket.getBeskrivelse() + "\t" + ticket.getAntall());

            int ticketId = validateIntInput("id of ticket you want to buy");
            int ticketAmount = validateIntInput("How many tickets");


            if (ticketAmount > arrangement.getMaxAttendees()) {
                throw new IOException("No more tickets left");

            } else {
                purchaseTicket(ticketId, ticketAmount);
            }
        } else
            System.out.println("This event doesn't have any available tickets");
    }

    private void purchaseTicket(int ticketID, int ticketAmount) {

        String name = validateStringInput("Type name");

        System.out.println("and confirmation method:");
        ArrayList<String> confirmationMethods = new ArrayList<>(){{
            add("Epost");
            add("Print");
        }};
        int alternativ2 = selectFromList(confirmationMethods);
        switch (alternativ2) {
            case 1:
                String epost = validateStringInput("Epost");
                break;
            case 2:
                System.out.println("Print");
                break;
        }

        System.out.println("Velg betalingsmåte: ");
        ArrayList<String> paymentMethods = new ArrayList<>(){{
            add("Bankkort");
            add("Kontanter");
            add("Vipps");
        }};
        int alternativ3 = selectFromList(paymentMethods);

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

package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Arrangement;

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

        // TODO:: print all types of arrangements tickets
        selectFromList(arrangement.getAvailableTickets());

        int ticketAmount = validateIntInput("How many tickets");


        if (ticketAmount > arrangement.getMaxAttendees()){
            throw new IOException("No more tickets left");

        }

        // TODO:: Update the arrangement class instance

        // TODO:: if user is logged in call db.registeredUserPurchasedTickets() else call db.ticketsHaveBeenPurchasedFromEvent()
        try {
            db.ticketsHaveBeenPurchasedFromEvent(1, ticketAmount);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}

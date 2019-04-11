package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Arrangement;
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

        }

        // TODO:: Update the arrangement class instance

        // TODO:: db registration of purchased tickets

    }

}

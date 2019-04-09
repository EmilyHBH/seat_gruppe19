package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Arrangement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerConsole extends Console{

    public static void customerMenu(){

        java.io.Console console = System.console();
        if (console == null){
            throw new NullPointerException("No console found");
        }

        System.out.println("Choose arrangement based on ID");

        // Prints all events
        try {
            ArrayList<Arrangement> allEvents = db.displayEvents();
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
        java.io.Console console = System.console();
        if (console == null){
            throw new NullPointerException("No console found");
        }

        System.out.println("How many tickets?");
        String ticketAmount = console.readLine(">");


        if (Integer.parseInt(ticketAmount) > arrangement.getMaxAttendees()){
            throw new IOException("No more tickets left");

        }

    }

}

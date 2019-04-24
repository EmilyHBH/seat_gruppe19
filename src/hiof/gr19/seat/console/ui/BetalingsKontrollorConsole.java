package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.model.Arrangement;

import java.sql.SQLException;
import java.util.ArrayList;

public class BetalingsKontrollorConsole extends Console {

    Arrangement arrangement;

    @Override
    public void start() {

        super.start();

        arrangement = registerWhichEventYouAreControllingTicketsFor();

        System.out.println("You chose " + arrangement.toString());

        checkMultipleTickets();

    }

    private Arrangement registerWhichEventYouAreControllingTicketsFor(){

        try {

            ArrayList<Arrangement> events = db.getEvents();

            PrintTables.printArrangements(events);

            int id = -1;

            while(id < 0 || id > events.size() -1)
                id = InputValidator.validateIntInput("Which event are you checking tickets for");

            return db.getEventById(events.get(id).getArrangementID());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

    private void checkMultipleTickets(){

        boolean run = true;

        while(run){

            int ticketID = InputValidator.validateIntInput("Ticket id");
            int amount = InputValidator.validateIntInput("Amount of people the ticket is valid for");

            System.out.println(isTicketIdValid(ticketID, amount));

            run = InputValidator.askBooleanQuestionAndReturnAnswer("Check another ticket");
        }

    }

    private boolean isTicketIdValid(int ticketId, int amount){

        try {
            boolean ticketIsValid = db.purchasedTicketIdValid(ticketId, amount, arrangement.getArrangementID());
            return ticketIsValid;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("The database is not reachable right now :/");
            e.printStackTrace();
        }

        return false;

    }

}

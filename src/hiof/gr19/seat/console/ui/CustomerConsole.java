package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Arrangement;
import hiof.gr19.seat.Ticket;

import java.sql.SQLException;
import java.util.ArrayList;

import static hiof.gr19.seat.console.ui.InputValidator.*;
import static hiof.gr19.seat.console.ui.PrintTables.printArrangements;
import static hiof.gr19.seat.console.ui.PrintTables.printTickets;

public class CustomerConsole extends CustomerInteractions implements StartAndFinish{

    @Override
    public void start() {
        if (scanner == null)
            throw new NullPointerException("No console found");

        viewMenu();
    }
    @Override
    public void finish(){
        System.out.println("Closing program");
        System.exit(0);
    }

    @Override
    void viewMenu(){
        int menuOptionChosen = selectFromList(menuListOfInteractions);

        switch(menuOptionChosen){
            case 1:
                run();
                break;
            case 2:
                finish();
                break;
            default:
                System.out.println("That was not a valid option");
                break;
        }

        // Recursive call so that the program will return to main menu instead of shut down
        viewMenu();
    }

    @Override
    void showEvents(ArrayList<Arrangement> events){
        printArrangements(events);
    }

    @Override
    Arrangement chooseEvent(ArrayList<Arrangement> events) {

        int arrangmentID = -1;

        while(arrangmentID < 0 || arrangmentID > events.size() -1)
            arrangmentID = validateIntInput("Which event do you want to buy ticket for? answer by using the id");

        try {
            return db.getEventById(events.get(arrangmentID).getArrangementID());
        }catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    void showTickets(Arrangement event) {
        System.out.println("\nThe tickets this event offers:\n");

        ArrayList<Ticket> tickets = event.getAvailableTickets();

        if(tickets.size() > 0)
            printTickets(tickets);

        else
            System.out.println("This event doesn't have any available tickets");
    }

    @Override
    int[] chooseTicketAndAmount() {

        int ticketId = validateIntInput("id of ticket you want to buy");
        int ticketAmount = validateIntInput("How many tickets");

        // TODO:: confirm ticket amount is valid

        return new int[]{ticketId, ticketAmount};
    }

    @Override
    int receiveConfirmationOfTicket() {

        System.out.println("Confirmation method:");
        int confirmationMethod = selectFromList(confirmationMethods);

        return confirmationMethod;
    }

    @Override
    int decidePaymentMethod(){
        System.out.println("Velg betalingsmåte: ");
        return selectFromList(paymentMethods);
    }

    @Override
    String ticketOwnersName() {
        return validateStringInput("Your name");
    }

    @Override
    int ticketOwnersMobileNr() {
        return validateIntInput("Your mobile number");
    }

    @Override
    String getEmail(){
        return validateStringInput("Email");
    }

    @Override
    boolean continueBuyingTickets(){
        return askBooleanQuestionAndReturnAnswer("Vil du kjøpe flere biletter?");
    }

    //TheBigRefactor
    //TODO: Mulig denne bør bli sett på.
    private String buildTicketPrint(ArrayList<Ticket> tickets) {
        StringBuilder sb = new StringBuilder();

        for (Ticket ticket : tickets)
            sb.append(ticket.getId() + "\t" + ticket.getBeskrivelse() + "\t" + ticket.getAntall());

        return sb.toString();
    }

    //Vet ikke om denne blir nødvendig spørs test progress
    private class purchase {
        Arrangement arrangement;
        //evt Ticket ticket isteden for int ticketID
        //Tror egentlig alle variablene i denne klassen burde ha vært egene klasser.
        int ticketID;
        int ticketAmount;
        int confirmationMethod;
        int paymentMethod;
        boolean paymentStatus;

        public purchase(Arrangement arrangement) {
            this.arrangement = arrangement;
        }
    }
}

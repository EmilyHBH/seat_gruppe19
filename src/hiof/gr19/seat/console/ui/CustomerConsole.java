package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Arrangement;
import hiof.gr19.seat.stubs.BetalingsStub;
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
                run();
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

    //TheBigRefactor
    //Dette er "runtime" metoden som sender brukeren igjennom hele kjøps prosessen.
    //Dette er da istedenfor metodelenking / method chaining
    private void run(){
        boolean running = true;

        ArrayList<Arrangement> events = new ArrayList<>();
        try{
            events = getArrangements();

        }catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
            running = false;
        }

        while(running) {

            showEvents(events);
            Arrangement valgtArrangement = selectEvent(events);

            int[] ticketidAndAmount;

            try {
                ticketidAndAmount = purchaseTicketMenu(valgtArrangement);
            }catch(IOException ex){
                ex.printStackTrace();
                break;
            }

            purchaseTicket(ticketidAndAmount[0], ticketidAndAmount[1]);

            running = buyMoreTicketsYN(); //Hadde en kunne hatt spørsmålstegn i metode navn hadde det vært det her.
        }
        super.start(); //Tilbake til start
    }

    private void showEvents(ArrayList<Arrangement> events){

        // Prints all events
        printArrangements(events);
    }

    private ArrayList<Arrangement> getArrangements() throws SQLException, ClassNotFoundException {
        return db.getEvents();
    }

    //TheBigRefactor
    protected Arrangement selectEvent(ArrayList<Arrangement> events) {
        // Start purchaseticketmenu on seleted event
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


    private int[] purchaseTicketMenu(Arrangement arrangement) throws IOException {

        System.out.println("\nThe tickets this event offers:\n");

        ArrayList<Ticket> tickets = arrangement.getAvailableTickets();

        if(tickets.size() > 0) {

            // Print all the arrangements different types of tickets
            //TheBigRefactor
            printTickets(tickets);

            int ticketId = selectTicketID();
            int ticketAmount = selectTicketAmount();


            if (ticketAmount > arrangement.getMaxAttendees()) {
                throw new IOException("No more tickets left");

            } else {
                int[] idAndAmount = {ticketId, ticketAmount};
                return idAndAmount;
            }
        } else
            System.out.println("This event doesn't have any available tickets");
        return null;
    }

    //TheBigRefactor
    //Eksisterer så den kan testes
    private int selectTicketAmount() {
        return validateIntInput("How many tickets");
    }

    //TheBigRefactor
    //Eksisterer så den kan testes
    private int selectTicketID() {
        return validateIntInput("id of ticket you want to buy");
    }

    //TheBigRefactor
    //TODO: Mulig denne bør bli sett på.
    private String buildTicketPrint(ArrayList<Ticket> tickets) {
        StringBuilder sb = new StringBuilder();

        for (Ticket ticket : tickets)
            sb.append(ticket.getId() + "\t" + ticket.getBeskrivelse() + "\t" + ticket.getAntall());

        return sb.toString();
    }

    private void purchaseTicket(int ticketID, int ticketAmount) {

        String name = validateStringInput("Type name");

        System.out.println("and confirmation method:");
        ArrayList<String> confirmationMethods = new ArrayList<>(){{
            add("Epost");
            add("Print");
        }};

        int confirmationMethod = selectFromList(confirmationMethods);
        selectConfirmationMethod(confirmationMethod);

        System.out.println("Velg betalingsmåte: ");
        ArrayList<String> paymentMethods = new ArrayList<>(){{
            add("Bankkort");
            add("Kontanter");
            add("Vipps");
        }};

        int paymentMethod = selectFromList(paymentMethods);

        confirmPurchase(ticketID, ticketAmount, name, paymentMethod);
    }

    private void confirmPurchase(int ticketID, int ticketAmount, String name, int paymentMethod) {
        BetalingsStub betaling = selectPaymentMethod(name, paymentMethod);

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

    protected BetalingsStub selectPaymentMethod(String name, int paymentMethod) {
        BetalingsStub betaling;
        betaling = null;

        switch (paymentMethod) {
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
                int telefonnummer = validateIntInput("Ditt telefonnummer: ");
                betaling = new BetalingsStub(telefonnummer, name);
                break;
            default:
                break;
        }
        return betaling;
    }

    private void selectConfirmationMethod(int confirmationMethod) {
        switch (confirmationMethod) {
            case 1:
                String epost = validateStringInput("Epost");
                break;
            case 2:
                System.out.println("Print");
                break;
            default:
                //exception of some kind
                break;
        }
    }

    private boolean buyMoreTicketsYN(){
        return askBooleanQuestionAndReturnAnswer("Ønsker du å kjøpe flere billetter?");
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

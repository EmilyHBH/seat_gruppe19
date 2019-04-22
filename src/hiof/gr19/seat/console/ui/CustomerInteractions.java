package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Arrangement;
import hiof.gr19.seat.Database;
import hiof.gr19.seat.stubs.BetalingsStub;
import hiof.gr19.seat.stubs.ConfirmationMethod;
import hiof.gr19.seat.stubs.emailReciept;
import hiof.gr19.seat.stubs.printReciept;

import java.sql.SQLException;
import java.util.ArrayList;

abstract class CustomerInteractions {

    Database db = new Database();
    BetalingsStub betalingsStub;
    ConfirmationMethod confirmationMethod;

    ArrayList<String> menuListOfInteractions = new ArrayList<>(){{
        add("View events");
        add("Exit");
    }};
    ArrayList<String> confirmationMethods = new ArrayList<>(){{
        add("Epost");
        add("Print");
    }};
    ArrayList<String> paymentMethods = new ArrayList<>(){{
        add("Bankkort");
        add("Kontanter");
        add("Vipps");
    }};

    // These are defined by the children of this abstract class. They define GUI interactions
    abstract void viewMenu();
    abstract void showEvents(ArrayList<Arrangement> events);
    abstract Arrangement chooseEvent(ArrayList<Arrangement> arrangements);
    abstract void showTickets(Arrangement event);
    abstract int[] chooseTicketAndAmount();
    abstract int receiveConfirmationOfTicket();
    abstract int decidePaymentMethod();
    abstract String ticketOwnersName();
    abstract int ticketOwnersMobileNr();
    abstract boolean continueBuyingTickets();
    abstract String getEmail();

    // These are method flows/chains, and are the same for all children/different gui classes
    private void performTicketPurchase(Arrangement event){

        showTickets(event);
        int[] tixIdAndAmount = chooseTicketAndAmount();
        establishPaymentMethod();
        establishConfirmationMethod();

        boolean betalingGodkent = betalingsStub.godkjentBetaling();

        if (betalingGodkent) {
            try {
                db.ticketsHaveBeenPurchasedFromEvent(tixIdAndAmount[0], tixIdAndAmount[1]);
                // TODO:: confirmation stub
            }catch (SQLException | ClassNotFoundException ex){
                ex.printStackTrace();
            }
        }

        // TODO this won't output on different systems? System.out.println("Betalingen var: " + betalingGodkent);
    }
    void establishPaymentMethod() {

        String name = ticketOwnersName();
        int paymentMethod = decidePaymentMethod();

        switch (paymentMethod) {
            case 1: // Bankkort
                betalingsStub = new BetalingsStub(name);
                break;
            case 2: // Kontanter
                betalingsStub = new BetalingsStub(name);
                break;
            case 3: // Vipps
                int telefonnummer = ticketOwnersMobileNr();
                betalingsStub = new BetalingsStub(telefonnummer, name);
                break;
            default:
                break;
        }
    }
    void establishConfirmationMethod() {

        int confirmationMethodType = receiveConfirmationOfTicket();

        switch (confirmationMethodType) {
            case 1: // Epost
                String email = getEmail();
                confirmationMethod = new emailReciept(email);
                break;
            case 2: // Print
                confirmationMethod = new printReciept();
                break;
            default:
                break;
        }

        confirmationMethod.sendConfirmation();
    }

    //TheBigRefactor
    //Dette er "runtime" metoden som sender brukeren igjennom hele kjøps prosessen.
    //Dette er da istedenfor metodelenking / method chaining
    void run(){

        boolean running = true;

        ArrayList<Arrangement> events = new ArrayList<>();
        try{
            events = db.getEvents();

        }catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
            running = false;
        }

        while(running) {

            showEvents(events);
            Arrangement valgtArrangement = chooseEvent(events);
            performTicketPurchase(valgtArrangement);

            running = continueBuyingTickets(); //Hadde en kunne hatt spørsmålstegn i metode navn hadde det vært det her.
        }
    }

}

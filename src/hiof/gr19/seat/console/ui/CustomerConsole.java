package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.model.Purchase;
import hiof.gr19.seat.model.Arrangement;
import hiof.gr19.seat.stubs.confirmation.ConfirmationMethod;
import hiof.gr19.seat.stubs.confirmation.EmailReciept;
import hiof.gr19.seat.stubs.confirmation.PrintReciept;
import hiof.gr19.seat.stubs.payment.*;
import hiof.gr19.seat.model.Ticket;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerConsole extends Console{

    @Override
    public void start() {

        super.start();

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

            Purchase thisPurchase = new Purchase(valgtArrangement);

			introduceArrangementTickets(valgtArrangement);
			int ticketId = selectTicketID();
			int ticketAmount = selectTicketAmount();
			//TODO: Her trenger vi sjekk om det er nok billetter
			//Tenker det hadde vært bra som en methode i Purchase men kanskje ikke for den har ikke DB
            thisPurchase.setTicketID(ticketId);
            thisPurchase.setTicketAmount(ticketAmount);

			thisPurchase.setOwnerName(enterNameOfTicketOwner());

			thisPurchase.setConfirmationMethod(declareConfirmationMethod());
			thisPurchase.setPaymentMethod(declarePaymentMethod());

			confirmPurchase(thisPurchase);

			thisPurchase.printReceipt();

            running = buyMoreTicketsYN(); //Hadde en kunne hatt spørsmålstegn i metode navn hadde det vært det her.
        }
        super.start(); //Tilbake til start
    }

    private String enterNameOfTicketOwner() {
        return validateStringInput("Type name");
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

    private void introduceArrangementTickets(Arrangement arrangement){
        System.out.println("\nThe tickets this event offers:\n");

        ArrayList<Ticket> tickets = arrangement.getAvailableTickets();

        if(tickets.size() > 0) {

            // Print all the arrangements different types of tickets
            //TheBigRefactor
            printTickets(tickets);

        } else
            System.out.println("This event doesn't have any available tickets");
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

	private PaymentMethod declarePaymentMethod() {
		int paymentMethod = introducePaymentMethods();
		return selectPaymentMethod(paymentMethod);
	}

	private ConfirmationMethod declareConfirmationMethod() {
		int confirmationMethod = introduceConfirmationMethods();
		return selectConfirmationMethod(confirmationMethod);
	}

	private int introducePaymentMethods() {
        System.out.println("Velg betalingsmåte: ");
        ArrayList<String> paymentMethods = new ArrayList<>(){{
            add("Bankkort");
            add("Kontanter");
            add("Vipps");
        }};

        return selectFromList(paymentMethods);
    }

    private int introduceConfirmationMethods() {
        System.out.println("and confirmation method:");
        ArrayList<String> confirmationMethods = new ArrayList<>(){{
            add("Epost");
            add("Print");
        }};

        return selectFromList(confirmationMethods);
    }

    private void confirmPurchase(Purchase purchase) {

        boolean betalingGodkent = purchase.getPaymentMethod().pay(purchase.getOwnerName());

        if (betalingGodkent) {
            try {
                db.ticketsHaveBeenPurchasedFromEvent(purchase.getTicketID(), purchase.getTicketAmount());
            }catch (SQLException | ClassNotFoundException ex){
                ex.printStackTrace();
            }
        }
        System.out.println("Payment accepted: " + betalingGodkent);
    }

    protected PaymentMethod selectPaymentMethod(int paymentMethod) {
        switch (paymentMethod) {
            case 1:
                System.out.println("Selected: Card");
                return new PayWithCard();
            case 2:
                System.out.println("Selected: Cash");
                return new PayWithCash();
            case 3:
                System.out.println("Selected: Vipps");
                int telefonnummer = enterPhoneNumberOfTicketOwner();
                return new PayWithVipps(telefonnummer);
            default:
                //exception of some kind
                break;
        }
        return null;
    }

    private int enterPhoneNumberOfTicketOwner() {
        return validateIntInput("Ditt telefonnummer");
    }

    private ConfirmationMethod selectConfirmationMethod(int confirmationMethod) {
        switch (confirmationMethod) {
            case 1:
                System.out.println("Selected: Email");
                String email = enterEmailOfTicketOwner();
                return new EmailReciept(email);
            case 2:
                System.out.println("Selected: Print");
            	return new PrintReciept();
            default:
                //exception of some kind
                break;
        }
        return null;
    }

    private String enterEmailOfTicketOwner() {
        return validateStringInput("Enter email of ticket owner");
    }

    private boolean buyMoreTicketsYN(){
        return askBooleanQuestionAndReturnAnswer("Ønsker du å kjøpe flere billetter?");
    }
}

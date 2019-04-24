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

        ArrayList<String> menuListOfFunctions = new ArrayList<String>(){{
            add("View events");
            add("Exit");
        }};

        int menuOptionChosen = InputValidator.selectFromList(menuListOfFunctions);

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
			int ticketId = selectTicketID(valgtArrangement.getAvailableTickets());
			int ticketAmount = selectTicketAmount(ticketId,valgtArrangement);
			//TODO: Her trenger vi sjekk om det er nok billetter
			//Tenker det hadde vært bra som en methode i Purchase men kanskje ikke for den har ikke DB
            thisPurchase.setTicket(selectTicket(thisPurchase.getArrangement(), ticketId));
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
        return InputValidator.validateStringInput("Type name");
    }

    protected void showEvents(ArrayList<Arrangement> events){
        // Prints all events
        PrintTables.printArrangements(events);
    }

    protected ArrayList<Arrangement> getArrangements() throws SQLException, ClassNotFoundException {
        return db.getEvents();
    }

    //TheBigRefactor
    protected Arrangement selectEvent(ArrayList<Arrangement> events) {
        // Start purchaseticketmenu on seleted event
        int arrangmentID = -1;

        while(arrangmentID < 0 || arrangmentID > events.size() -1)
            arrangmentID = InputValidator.validateIntInput("Which event do you want to buy ticket for? answer by using the id");

        try {
            return db.getEventById(events.get(arrangmentID).getArrangementID());

        }catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    protected Ticket selectTicket(Arrangement arrangement, int ticketId){

        ArrayList<Ticket> tickets = arrangement.getAvailableTickets();
        for (Ticket billett : tickets){
            if (ticketId == billett.getId())
                return billett;
        }
        return null;
    }

    private void introduceArrangementTickets(Arrangement arrangement){
        System.out.println("\nThe tickets this event offers:\n");

        ArrayList<Ticket> tickets = arrangement.getAvailableTickets();

        if(tickets.size() > 0) {

            // Print all the arrangements different types of tickets
            //TheBigRefactor
            PrintTables.printTickets(tickets);

        } else
            System.out.println("This event doesn't have any available tickets");
    }

    //TheBigRefactor
    //Eksisterer så den kan testes
    protected int selectTicketAmount(int ticketId, Arrangement arrangement) {
        int requestedAmountToBuy = InputValidator.validateIntInput("How many tickets");

        while(!arrangement.checkTicketConditions(ticketId,requestedAmountToBuy)) {
            System.out.println("Not enough tickets to buy");
            requestedAmountToBuy = InputValidator.validateIntInput("How many tickets");
        }


        return requestedAmountToBuy;
    }

    //TheBigRefactor
    //Eksisterer så den kan testes
    private int selectTicketID(ArrayList<Ticket> tickets) {

        int ticketId = InputValidator.validateIntInput("id of ticket you want to buy");

        while(ticketId >= tickets.size() || ticketId < 0){
            System.out.println("Input out of bounds. Try again");
            ticketId = InputValidator.validateIntInput("id of ticket you want to buy");
        }

        int ticketIdInDB = tickets.get(ticketId).getId();

        return ticketIdInDB;
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
        ArrayList<String> paymentMethods = new ArrayList<String>(){{
            add("Bankkort");
            add("Kontanter");
            add("Vipps");
        }};

        return InputValidator.selectFromList(paymentMethods);
    }

    private int introduceConfirmationMethods() {
        System.out.println("and confirmation method:");
        ArrayList<String> confirmationMethods = new ArrayList<String>(){{
            add("Epost");
            add("Print");
        }};

        return InputValidator.selectFromList(confirmationMethods);
    }

    private void confirmPurchase(Purchase purchase) {

        boolean betalingGodkent = purchase.getPaymentMethod().pay(purchase.getOwnerName());

        if (betalingGodkent) {
            try {
                db.ticketsHaveBeenPurchasedFromEvent(purchase.getTicket().getId(), purchase.getTicketAmount());
                purchase.registerPurchaseInDb();
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
        return InputValidator.validateIntInput("Ditt telefonnummer");
    }

    protected ConfirmationMethod selectConfirmationMethod(int confirmationMethod) {
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
        return InputValidator.validateStringInput("Enter email of ticket owner");
    }

    private boolean buyMoreTicketsYN(){
        return InputValidator.askBooleanQuestionAndReturnAnswer("Ønsker du å kjøpe flere billetter?");
    }
}

package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.model.Arrangement;
import hiof.gr19.seat.model.Organizer;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrganizerConsole extends Console{

    private Organizer user;

    @Override
    public void start() {
        super.start();
        user = loginOrRegister();
        organizerMenu();
    }

    private Organizer loginOrRegister(){
        if(InputValidator.askBooleanQuestionAndReturnAnswer("Do you already have an account"))
            return organizerLogin();
        else
            return registerOrganizer();
    }

    protected Organizer organizerLogin(){

        String organizerName = InputValidator.validateStringInput("Organizer Name");

        //Blir man hardstuck i denne?
        try {
            boolean organizerStatus = db.checkForOrganizer(organizerName);
            if (organizerStatus){
                System.out.println("Welcome " + organizerName);
                return db.getOrganizerByName(organizerName);
            }else {
                System.out.println(organizerName + " Is not registered");
                organizerLogin();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

	protected Organizer registerOrganizer(){

		String organizerName = InputValidator.validateStringInput("Name of organization");

		try {
			boolean organizerStatus = db.checkForOrganizer(organizerName);
			if (organizerStatus){
                System.out.println("This organization name already exists, choose another");
				registerOrganizer();
			} else {
				String email = InputValidator.validateStringInput("Email");

				// input new organizer to db
				try {
					return db.addOrganizer( organizerName, email);
				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

    private void organizerMenu(){

        ArrayList<String> menuListOfFunctions = new ArrayList<String>(){{
            add("Create event");
            add("Add an additional type of ticket to an event");
            add("Change an events information");
            add("Exit");
        }};

        int menuOptionChosen = InputValidator.selectFromList(menuListOfFunctions);

        switch(menuOptionChosen){
            case 1:                                 // Create Event
                organizerCreatesArrangement();
                break;
            case 2:                                 // Add additional ticket to event
                organizerAddsAdditionalTicket();
                break;
            case 3:                                 // Change an events information
                organizerChangesEventInformation();
                break;
            case 4:
                finish();                           // Exits the program
                break;
            default:
                System.out.println("That was not a valid option");
                break;
        }

        // Recursive call so that the program will return to main menu instead of shut down
        organizerMenu();

    }

    private void organizerChangesEventInformation() {
        try {
            ArrayList<Arrangement> thisOrganizationsEvents = db.getEventsByOrganizer(user.getOrganizerID());

            if(thisOrganizationsEvents.size() > 0) {

                PrintTables.printArrangements(thisOrganizationsEvents);

                int arrangementId = InputValidator.validateIntInput("Select event");
                Arrangement arrangementToChangeinfo = db.getEventById(thisOrganizationsEvents.get(arrangementId).getArrangementID());

                changeEventInfo(arrangementToChangeinfo);
            } else
                System.out.println("You have no events.");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void organizerAddsAdditionalTicket() {
        try {
            PrintTables.printArrangements(db.getEventsByOrganizer(user.getOrganizerID()));

            int arrangementId = InputValidator.validateIntInput("Select event");
            Arrangement arrangementToMakeTicketsFor = db.getEventById(arrangementId);

            defineMultipleTicketes(arrangementToMakeTicketsFor);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void organizerCreatesArrangement(){

        // Instantiate an event based on user input
        Arrangement createdArrangement = createArrangmentPrompt();

        // Add the event into the database
        try {
            db.createEvent(createdArrangement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        defineMultipleTicketes(createdArrangement);
    }

    private Arrangement createArrangmentPrompt() {

        System.out.println("Create arrangment");

        String title = InputValidator.validateStringInput("Title:");
        String description = InputValidator.validateStringInput("Description:");
        String dateString = InputValidator.validateStringInput("Date day-month-year:"); // TODO:: validation method
        int ticketAmount = InputValidator.validateIntInput("How many tickets?:");
        String location = InputValidator.validateStringInput("Location:");

        return new Arrangement(
                -1,//Blir satt av database
                title,
                description,
                parseDate(dateString),
                user,
                ticketAmount,
                location,
                null);  // Blir satt etterpå

    }

    private void defineMultipleTicketes(Arrangement arrangement){
        while(InputValidator.askBooleanQuestionAndReturnAnswer("Add a ticket type")){

            int antall = InputValidator.validateIntInput("Antall billetter:");
            int pris = InputValidator.validateIntInput("Pris:");
            String bilettBeskrivelse = InputValidator.validateStringInput("Bilett beskrivelse");

            try {
                db.defineArrangementTickets(arrangement.getArrangementID(),antall,pris, bilettBeskrivelse);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void changeEventInfo(Arrangement arrangement){
        ArrayList<String> thingsToChange = new ArrayList<String>(){{
            add("Name");
            add("Description");
            add("Date");
            add("I'm done changing");
        }};

        System.out.println("What do you want to change?");

        boolean exitLoop = false;

        while(!exitLoop) {

            int optionToChange = InputValidator.selectFromList(thingsToChange);

            switch (optionToChange) {
                case 1:
                    arrangement.setArrangmentTitle(InputValidator.validateStringInput("New name"));
                    break;
                case 2:
                    arrangement.setArrangmentDescription(InputValidator.validateStringInput("New description"));
                    break;
                case 3:
                    arrangement.setArragmentDate(parseDate(InputValidator.validateStringInput("New date (d-mm-yyyy)")));
                    break;
                default:
                    exitLoop = true;
                    break;
            }
        }

        try {
            db.changeEventInfo(arrangement.getArrangementID(), arrangement.getArrangmentTitle(), arrangement.getArrangmentDescription(), arrangement.getArragmentDateInStringFormat());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Arrangement;
import hiof.gr19.seat.Database;
import hiof.gr19.seat.Organizer;

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
        if(askBooleanQuestionAndReturnAnswer("Do you already have an account"))
            return organizerLogin();
        else
            return registerOrganizer();
    }

    private Organizer organizerLogin(){

        String organizerName = validateStringInput("Organizer Name");

        try {
            boolean organizerStatus = db.checkForOrganizer(organizerName);
            if (organizerStatus){
                console.printf("Welcome " + organizerName +"\n");
                return db.getOrganizerByName(organizerName);
            }else {
                console.printf(organizerName + " Is not registered\n");
                organizerLogin();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void organizerMenu(){

        ArrayList<String> menuListOfFunctions = new ArrayList<>(){{
            add("Create event");
            add("Add an additional type of ticket to an event");
            add("Change an events information");
            add("Exit");
        }};

        int menuOptionChosen = selectFromList(menuListOfFunctions);

        switch(menuOptionChosen){
            case 1:                                 // Create Event
                organizerCreatesArrangement();
                break;
            case 2:                                 // Add additional ticket to event
                try {
                    printArrangements(db.getEventsByOrganizer(user.getOrganizerID()));

                    int arrangementId = validateIntInput("Select event");
                    Arrangement arrangementToMakeTicketsFor = db.getEventById(arrangementId);

                    defineMultipleTicketes(arrangementToMakeTicketsFor);

                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 3:                                 // Change an events information
                try {
                    ArrayList<Arrangement> thisOrganizationsEvents = db.getEventsByOrganizer(user.getOrganizerID());

                    if(thisOrganizationsEvents.size() > 0) {

                        printArrangements(thisOrganizationsEvents);

                        int arrangementId = validateIntInput("Select event");
                        Arrangement arrangementToChangeinfo = db.getEventById(arrangementId);

                        changeEventInfo(arrangementToChangeinfo);
                    } else
                        System.out.println("You have no events.");

                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
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

        String title = validateStringInput("Title:");
        String description = validateStringInput("Description:");
        String dateString = validateStringInput("Date day-month-year:"); // TODO:: validation method
        int ticketAmount = validateIntInput("How many tickets?:");
        String location = validateStringInput("Location:");

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
        while(askBooleanQuestionAndReturnAnswer("Add a ticket type")){

            int antall = validateIntInput("Antall billetter:");
            int pris = validateIntInput("Pris:");
            String bilettBeskrivelse = validateStringInput("Bilett beskrivelse");

            try {
                db.defineArrangementTickets(arrangement.getArrangementID(),antall,pris, bilettBeskrivelse);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private Organizer registerOrganizer(){

        String organizerName = validateStringInput("Name of organization");

        try {
            boolean organizerStatus = db.checkForOrganizer(organizerName);
            if (organizerStatus){
                console.printf("This organization name already exists, choose another");
                registerOrganizer();
            } else {
                String email = validateStringInput("Email");

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

    private void changeEventInfo(Arrangement arrangement){
        ArrayList<String> thingsToChange = new ArrayList<>(){{
            add("Name");
            add("Description");
            add("Date");
            add("I'm done changing");
        }};

        System.out.println("What do you want to change?");

        boolean exitLoop = false;

        while(!exitLoop) {

            int optionToChange = selectFromList(thingsToChange);

            switch (optionToChange) {
                case 1:
                    arrangement.setArrangmentTitle(validateStringInput("New name"));
                    break;
                case 2:
                    arrangement.setArrangmentDescription(validateStringInput("New description"));
                    break;
                case 3:
                    arrangement.setArragmentDate(parseDate(validateStringInput("New date (d-mm-yyyy)")));
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

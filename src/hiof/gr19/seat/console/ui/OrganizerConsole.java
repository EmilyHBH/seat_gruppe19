package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Arrangement;
import hiof.gr19.seat.Database;
import hiof.gr19.seat.Organizer;

import java.sql.SQLException;
import java.util.ArrayList;

import static hiof.gr19.seat.console.ui.Console.parseDate;
import static hiof.gr19.seat.console.ui.InputValidator.*;
import static hiof.gr19.seat.console.ui.PrintTables.printArrangements;

public class OrganizerConsole extends OrganizerInteractions implements StartAndFinish {

    @Override
    public void start() {
        if (scanner == null)
            throw new NullPointerException("No console found");

        loginOrRegister();
        viewMenu();
    }
    @Override
    public void finish() {
        System.out.println("Closing program");
        System.exit(0);
    }

    @Override
    boolean returningOrganizer() {
        return askBooleanQuestionAndReturnAnswer("Do you already have an account?");
    }

    @Override
    String getOrganizerNameOfExisting() {
        return validateStringInput("Organization name");
    }

    @Override
    String[] getRegistrationInfo() {
        String[] nameAndEmail = new String[2];
        nameAndEmail[0] = validateStringInput("Name");
        nameAndEmail[1] = validateStringInput("Email");
        return nameAndEmail;
    }

    @Override
    void createArrangement() {
        // Instantiate an event based on user input
        Arrangement createdArrangement = createArrangmentPrompt();

        // Add the event into the database
        addEventToDb(createdArrangement);

        defineMultipleTickets(createdArrangement);
    }

    @Override
    void changeEventInfo(Arrangement arrangement){

        System.out.println("What do you want to change?");

        boolean exitLoop = false;

        while(!exitLoop) {

            int optionToChange = selectFromList(eventInfoCapableOfChange);

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

        updateDBonEventInfoChanged(arrangement);
    }

    @Override
    void viewMenu(){

        int menuOptionChosen = selectFromList(menuListOfFunctions);

        switch(menuOptionChosen){
            case 1:                                 // Create Event
                createArrangement();
                break;
            case 2:                                 // Add additional ticket to event
                try {
                    printArrangements(db.getEventsByOrganizer(user.getOrganizerID()));

                    int arrangementId = validateIntInput("Select event");
                    Arrangement arrangementToMakeTicketsFor = db.getEventById(arrangementId);

                    defineMultipleTickets(arrangementToMakeTicketsFor);

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
        viewMenu();
    }

    @Override
    void defineMultipleTickets(Arrangement arrangement){
        while(askBooleanQuestionAndReturnAnswer("Add a ticket type")){

            int antall = validateIntInput("Antall billetter:");
            int pris = validateIntInput("Pris:");
            String bilettBeskrivelse = validateStringInput("Bilett beskrivelse");

            addTicketsToEvent(arrangement.getArrangementID(), antall, pris, bilettBeskrivelse);
        }
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

}

package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Arrangement;
import hiof.gr19.seat.Database;
import hiof.gr19.seat.Organizer;

import javax.xml.crypto.Data;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class OrganizerConsole extends Console{

    private Organizer user;

    @Override
    public void start() {
        super.start();
        loginOrRegister();
        organizerMenu();
    }

    private void loginOrRegister(){

        if(askBooleanQuestionAndReturnAnswer("Do you already have an account"))
            organizerLogin();
        else {
            user = registerOrganizer();

            // input new organizer to db
            try {
                db.addOrganizer(user);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void organizerLogin(){

        System.out.println("Organizer Name:");
        String organizerName = console.readLine(">");

        //TODO sjekk om den finnes i DB
        Database database = new Database();
        try {
            boolean organizerStatus = database.checkForOrganizer(organizerName);
            if (organizerStatus){
                console.printf("Welcome " + organizerName);
            }else {
                console.printf(organizerName + " Is not registered");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // TODO:: return Organizer?
    }

    private void organizerMenu(){

        // Instantiate an event based on user input
        Arrangement createdArrangement = createArrangmentPrompt();

        // Add the event into the database
        try {
            db.createEvent(createdArrangement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Define tickets, but only if the event is supposed to have a ticket system
        if(askBooleanQuestionAndReturnAnswer("Shall this event have tickets"))
            defineTickets(createdArrangement.getArrangementID());
    }

    private Arrangement createArrangmentPrompt() {

        System.out.println("Create arrangment");

        String title = console.readLine("Tittle:");
        String description = console.readLine("Description:");
        String dateString = console.readLine("Date day-month-year:"); //TODO test
        int ticketAmount = Integer.parseInt(console.readLine("How many tickets?:"));
        String location = console.readLine(">Location");

        return new Arrangement(
                -1,//Blir satt av database
                title,
                description,
                parseDate(dateString),
                user,
                ticketAmount,
                location);

    }

    private void defineTickets(int arrangementId){

        System.out.println("\nDefine tickets: \n");

        int antall = validateIntInput("Antall billetter:");
        int pris = validateIntInput("Pris:");

        try {
            db.defineArrangementTickets(arrangementId,antall,pris);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    Organizer registerOrganizer(){

        System.out.println("Name of organization");
        String organizerName = console.readLine(">");

        String email = validateStringInput("Email");
        System.out.println("Password");
        String passwordFirstEntry = String.valueOf(console.readPassword(">"));

        try {
            passwordFirstEntry = stringToSha1(passwordFirstEntry);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println("Repeat password");
        String passwordSecondEntry = String.valueOf(console.readPassword(">"));
        try {
            passwordSecondEntry = stringToSha1(passwordSecondEntry);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println(passwordFirstEntry);
        System.out.println(passwordSecondEntry);
        if (!passwordFirstEntry.equals(passwordSecondEntry)){
            System.out.println("Passwords do not match");
            return null;
        }




        //TODO sjekk om navnet er tatt
        Database database = new Database();
        try {
            boolean registred = database.checkForOrganizer(organizerName);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return new Organizer(null,organizerName, email, passwordFirstEntry);
    }

}

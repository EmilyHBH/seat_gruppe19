package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Arrangement;
import hiof.gr19.seat.Database;
import hiof.gr19.seat.Organizer;

import java.sql.SQLException;

public class OrganizerConsole extends Console{

    static void oranizerLogin(){

        java.io.Console console = System.console();
        if (console == null){
            throw new NullPointerException("No console found");
        }

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
    }

    static void organizerMenu(){

        java.io.Console console = System.console();
        if (console == null){
            throw new NullPointerException("No console found");
        }

        System.out.println("Create arrangment");
        System.out.println();

    }


    static void createArrangmentPrompt() {
        java.io.Console console = System.console();
        if (console == null) {
            throw new NullPointerException("No console found");
        }

        String title = console.readLine("Tittle:");
        String description = console.readLine("Description:");
        String dateString = console.readLine("Date day-month-year:"); //TODO test
        int ticketAmount = Integer.parseInt(console.readLine("How many tickets?:"));

        Arrangement arrangement = new Arrangement(
                null,//Blir satt av database
                title,
                description,
                parseDate(dateString),
                null, // dette blir enumen
                ticketAmount);

    }


    Organizer registerOrganizer(){
        java.io.Console console = System.console();
        if (console == null) {
            throw new NullPointerException("No console found");
        }

        System.out.println("Name of organization");
        String organizerName = console.readLine(">");

        //TODO sjekk om navnet er tatt

        return new Organizer(null,organizerName);
    }

}

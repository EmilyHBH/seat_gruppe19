package hiof.gr19.seat;

import java.sql.*;
import java.util.Scanner;

public class Seat {

    private static Database test = new Database();

    public static void main(String[] args){
        //identifyUser();

        // Denne funksjonen inneholder get, insert og delete eksempler for å sjekke om de funker
        tempFunctionToTestDatabaseConnectionWorks();
    }

    private static void identifyUser() {
        Scanner input = new Scanner(System.in);

        System.out.println("OP mode?");
        System.out.println("1 = Organizer");
        System.out.println("2 = Not Organizer");
        System.out.print(">");
        int systemMode = input.nextInt();

        switch (systemMode){
            case 1:
                System.out.println("organizer");
                //organizerMode()
                break;

            case 2:
                System.out.println("Customer");
                break;

            default:
                System.out.println("Please choose 1 or 2 :)");
                main(null);
                break;
        }
    }

    // Disse skal slettes, er der bare for å vise om db funksjonalitet
    private static void tempFunctionToTestDatabaseConnectionWorks(){
        try {
            ResultSet resultSet = test.displayUsers();
            System.out.println("Users in our system:");

            while(resultSet.next())
                System.out.println("-\t" + resultSet.getString("forNavn") + " " + resultSet.getString("etterNavn"));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            // Arrangøren her er et dummy eksempel og ikke en Organizer hentet fra databasen
            Arrangement exampleArr = new Arrangement(null, "Fyre Festival","no scam come",new Date(2019,5,1),new Organizer(1,"organiiiiizeyy"),50);
            test.createEvent(exampleArr);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ResultSet resultSet = test.displayEvents();
            System.out.println("\nEvents in our system:");

            while(resultSet.next())
                System.out.println("-\t" + resultSet.getString("navn") + " " + resultSet.getString("beskrivelse") + " " + resultSet.getString("dato") + " " + resultSet.getInt("arrangor_fk"));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            test.cancelEvent(1);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("An event should be deleted from the system.");

        try {
            ResultSet resultSet = test.displayEvents();
            System.out.println("\nEvents in our system:");

            while(resultSet.next())
                System.out.println("-\t" + resultSet.getString("navn") + " " + resultSet.getString("beskrivelse") + " " + resultSet.getString("dato") + " " + resultSet.getInt("arrangor_fk"));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

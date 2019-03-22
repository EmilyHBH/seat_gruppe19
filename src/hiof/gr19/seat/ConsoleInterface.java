package hiof.gr19.seat;

import de.vandermeer.asciitable.AsciiTable;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ConsoleInterface {

     private static User.Type identifyUser() {

        Console console = System.console();
        if (console == null){
            throw new NullPointerException("No console found");
        }

        System.out.println("OP mode?");
        System.out.println("1 = Organizer");
        System.out.println("2 = Customer");
        String input = console.readLine(">");

        switch (input){
            case "1":
                return User.Type.Organizer;

            case "2":
                return User.Type.Customer;

            default:
                System.out.println("Choose 1 or 2");
                break;
        }

        return null;
    }

    private void customerMenu(){

        Console console = System.console();
        if (console == null){
            throw new NullPointerException("No console found");
        }

        System.out.println("Choose arrangement based on ID");
        //TODO print all arrangments

        String arrangmentID = console.readLine(">");
        //TODO metode for å hente arrangement basert på id


        //TODO sjekk at det er nok billeter og at dato passer
        System.out.println("How many tickets?");
        String ticketAmount = console.readLine(">");



    }


    void getByIDarrangment(){



    }

    void checkTicket(){

    }

    void printArrangements(ArrayList<Arrangement> arrangementList){

        AsciiTable arragmentTable = new AsciiTable();

        arragmentTable.addRule();
        arragmentTable.addRow("ID","Arrangment","Date","Ticketavaliable");

        for (Arrangement x: arrangementList) {
            arragmentTable.addRule();
            arragmentTable.addRow(x.getArrangementID(),x.getArrangmentTitle(),x.getArragmentDate(),x.getPeopleAmount());
            arragmentTable.addRule();

        }

        String table = arragmentTable.render();
        System.out.println(table);

    }



}

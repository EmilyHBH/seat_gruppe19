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

    void printArrangements(ArrayList<Arrangement> arrangementList){

        AsciiTable arragmentTable = new AsciiTable();

        arragmentTable.addRule();
        arragmentTable.addRow("Arrangment","Date","Ticketavaliable");

        for (Arrangement x: arrangementList) {
            arragmentTable.addRule();
            arragmentTable.addRow(x.getArrangmentTitle(),x.getArragmentDate(),x.getPeopleAmount());
            arragmentTable.addRule();

        }

        String table = arragmentTable.render();
        System.out.println(table);

    }

}

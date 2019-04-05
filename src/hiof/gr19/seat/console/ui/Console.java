package hiof.gr19.seat.console.ui;

import de.vandermeer.asciitable.AsciiTable;
import hiof.gr19.seat.Arrangement;
import hiof.gr19.seat.Database;
import hiof.gr19.seat.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Console {

    static Database db = new Database();

    public static User.Type identifyUser() {

        java.io.Console console = System.console();
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


    public static Date parseDate(String inputDate){

        DateFormat format = new SimpleDateFormat("d-MM-yyyy", Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;

    }


    public static void printArrangements(ArrayList<Arrangement> arrangementList){

        if (arrangementList.size() == 0){
            System.out.println("No events");
            return;
        }

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

    void getArrangementById(){

    }

    boolean checkTicketConditions(Arrangement arrangement, int ticketAmount) {

        return arrangement.getPeopleAmount() > ticketAmount;

    }

    public static void selectFromList(ArrayList arrayList){
        for (int i = 0; i < arrayList.size(); i++){
            System.out.println(i + " = " + arrayList.get(i));
        }

        java.io.Console console = System.console();
        String input = console.readLine(">");

    }


}

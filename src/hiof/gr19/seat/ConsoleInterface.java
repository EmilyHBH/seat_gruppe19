package hiof.gr19.seat;

import de.vandermeer.asciitable.AsciiTable;

import java.io.Console;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ConsoleInterface {

     static User.Type identifyUser() {

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

    protected void customerMenu(){

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

    static void organizerMenu(){

         Console console = System.console();
         if (console == null){
             throw new NullPointerException("No console found");
         }

         System.out.println("Create arrangment");
         System.out.println();

    }

    void createArrangmentPrompt() {
        Console console = System.console();
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
                null, //
                ticketAmount);


    }


    private Date parseDate(String inputDate){

        DateFormat format = new SimpleDateFormat("d-MM-yyyy", Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;

    }


    void getArrangementById(){

    }

    boolean checkTicketConditions(Arrangement arrangement, int ticketAmount) {

        return arrangement.getPeopleAmount() > ticketAmount;

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

    public static void selectFromList(ArrayList arrayList){
        for (int i = 0; i < arrayList.size(); i++){
            System.out.println(i + " = " + arrayList.get(i));
        }

        Console console = System.console();
        String input = console.readLine(">");

    }

}

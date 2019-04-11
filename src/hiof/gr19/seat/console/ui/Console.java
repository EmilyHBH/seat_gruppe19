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

    static java.io.Console console = System.console();
    static Database db = new Database();

    public static User.Type identifyUser() {

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

    // This method is meant to be overridden
    public void start(){
        if (console == null){
            throw new NullPointerException("No console found");
        }
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

    static boolean askBooleanQuestionAndReturnAnswer(String question){
        System.out.println(questionFormat(question + " (y/n)"));

        String answer;
        while(true){
             answer = console.readLine();

            if(answer.equals("y"))
                return true;
            else if(answer.equals("n"))
                return false;

            System.out.println("Answer by typing 'y' or 'n'");
        }
    }
    static int validateIntInput(String question){

        System.out.println(questionFormat(question));

        int result;

        while(true){
            try{
                result = Integer.parseInt(console.readLine());
                return result;
            }
            catch(NumberFormatException e){
                System.out.println("Only numeric whole numbers are valid. Try again:");
            }
        }
    }
    static String validateStringInput(String question){
        System.out.println(questionFormat(question));

        String result;

        while(true){
            try{
                result = console.readLine();
                return result;
            }
            catch(Exception e){
                System.out.println("Something went wrong, try again:");
            }
        }
    }
    private static String questionFormat(String question){
        return question == null ? null : "\n" + question + ":\n";
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
            arragmentTable.addRow(x.getArrangementID(),x.getArrangmentTitle(),x.getArragmentDate(),x.getMaxAttendees());
            arragmentTable.addRule();

        }

        String table = arragmentTable.render();
        System.out.println(table);

    }

    void getArrangementById(){

    }

    boolean checkTicketConditions(Arrangement arrangement, int ticketAmount) {

        return arrangement.getMaxAttendees() > ticketAmount;

    }

    public static void selectFromList(ArrayList arrayList){
        for (int i = 0; i < arrayList.size(); i++){
            System.out.println(i + " = " + arrayList.get(i));
        }

        java.io.Console console = System.console();
        String input = console.readLine(">");

    }
    static char getAscii(int code){

        return (char) code;
    }


}

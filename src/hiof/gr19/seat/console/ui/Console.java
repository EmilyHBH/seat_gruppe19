package hiof.gr19.seat.console.ui;

import de.vandermeer.asciitable.AsciiTable;
import hiof.gr19.seat.model.Arrangement;
import hiof.gr19.seat.Database;
import hiof.gr19.seat.model.Ticket;
import hiof.gr19.seat.model.User;

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

        System.out.println("1 = Organizer");
        System.out.println("2 = Customer");
        String input = validateStringInput("What are you");

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

    void finish(){
        System.out.println("Closing program");
        System.exit(0);
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
             answer = console.readLine(">");

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
                result = Integer.parseInt(console.readLine(">"));
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
                result = console.readLine(">");
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

    static void printArrangements(ArrayList<Arrangement> arrangementList){

        if (arrangementList.size() == 0){
            System.out.println("No events");
            return;
        }

        AsciiTable arragmentTable = new AsciiTable();

        arragmentTable.addRule();
        arragmentTable.addRow("ID","Arrangment","Date","Ticketavaliable");

        for (int i = 0; i < arrangementList.size(); i++) {
            arragmentTable.addRule();
            int ledigeBiletter = 0;
            for(Ticket ticket : arrangementList.get(i).getAvailableTickets())
                ledigeBiletter += ticket.getAntall();

            arragmentTable.addRow(i,arrangementList.get(i).getArrangmentTitle(),arrangementList.get(i).getArragmentDate(),ledigeBiletter);
            arragmentTable.addRule();

        }

        String table = arragmentTable.render();
        System.out.println(table);

    }

    static void printTickets(ArrayList<Ticket> tickets){
        if (tickets.size() == 0){
            System.out.println("No tickets");
            return;
        }

        AsciiTable ticketTable = new AsciiTable();

        ticketTable.addRule();
        ticketTable.addRow("ID", "Description", "Tickets avaliable", "Price");

        for (Ticket ticket : tickets){
            ticketTable.addRule();
            ticketTable.addRow(ticket.getId(), ticket.getBeskrivelse(), ticket.getAntall(), ticket.getPris());
            ticketTable.addRule();
        }

        String table = ticketTable.render();
        System.out.println(table);
    }

    //printEvents?? include asciiTable

    void getArrangementById(){

    }

    boolean checkTicketConditions(Arrangement arrangement, int ticketAmount) {

        return arrangement.getMaxAttendees() > ticketAmount;

    }

    static int selectFromList(ArrayList arrayList){
        for (int i = 1; i < arrayList.size() +1; i++)
            System.out.println(i + " = " + arrayList.get(i-1));

        int choice = validateIntInput("Make a choice based on the id/nr");

        while(choice < 1 && choice >= arrayList.size() +1){
            System.out.println("The number you gave is not in range, choose again");
            choice = validateIntInput("Make a choice based on the id/nr");
        }

        return choice;
    }

    static char getAscii(int code){

        return (char) code;
    }
}

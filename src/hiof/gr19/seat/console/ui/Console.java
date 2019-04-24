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
import java.util.Scanner;

import static hiof.gr19.seat.console.ui.InputValidator.validateStringInput;

public class Console {

    static Scanner scanner = new Scanner(System.in);
    static Database db = new Database();

    public static User.Type identifyUser() {

        if (scanner == null){
            throw new NullPointerException("No console found");
        }

        System.out.println("1 = Organizer");
        System.out.println("2 = Customer");
        System.out.println("3 = Ticket Checker");
        String input = validateStringInput("What are you");

        switch (input){
            case "1":

                return User.Type.Organizer;

            case "2":
                return User.Type.Customer;

            case "3":
                return User.Type.TicketChecker;

            default:
                System.out.println("Choose 1 or 2");
                break;
        }

        return null;
    }

    // This method is meant to be overridden
    public void start(){
        if (scanner == null){
            throw new NullPointerException("No console found");
        }
    }

    public void finish(){
        System.out.println("Exiting");
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

    //printEvents?? include asciiTable

    void getArrangementById(){

    }

    static char getAscii(int code){

        return (char) code;
    }
}

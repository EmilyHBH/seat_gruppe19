package hiof.gr19.seat;

import javax.xml.crypto.Data;
import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static hiof.gr19.seat.ConsoleInterface.identifyUser;
import static hiof.gr19.seat.ConsoleInterface.organizerMenu;

public class Seat {

    public static void main(String[] args){
        identifyUser();
    }

    private static void identifyUser() {
        Scanner input = new Scanner(System.in);

        System.out.println("OP mode?");
        System.out.println("1 = Organizer");
        System.out.println("2 = Not Organizer");
        System.out.print(">");
        int systemMode = Integer.parseInt(input.nextLine());

        switch (systemMode){
            case 1:
                OrganizerMode organizerMode = new OrganizerMode(input);
                organizerMode.start();
                break;

            case 2:
                Customer_mode customerMode = new Customer_mode(input);
                customerMode.start();
                break;

            default:
                System.out.println("Please choose 1 or 2 :)");
                main(null);
                break;
        }
    }
}

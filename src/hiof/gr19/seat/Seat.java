package hiof.gr19.seat;

import sun.awt.CustomCursor;

import javax.xml.crypto.Data;
import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static hiof.gr19.seat.ConsoleInterface.*;

public class Seat {

    public static void main(String[] args){
        identifyUser();
    }

    private static void identifyUser() {
        Scanner input = new Scanner(System.in);

        System.out.println("OP mode?");
        System.out.println("1 = Organizer");
        System.out.println("2 = Customer");
        System.out.print(">");
        int systemMode = Integer.parseInt(input.nextLine());

        switch (systemMode){
            case 1:

                break;

            case 2:
                ArrayList<Arrangement> arrangements = new ArrayList<>();
                printArrangements(arrangements);
                customerMenu();

                break;

            default:
                System.out.println("Please choose 1 or 2 :)");
                main(null);
                break;
        }
    }
}

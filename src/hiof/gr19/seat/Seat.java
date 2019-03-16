package hiof.gr19.seat;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

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
                System.out.println("Customer");
                break;

            default:
                System.out.println("Please choose 1 or 2 :)");
                main(null);
                break;
        }
    }
}

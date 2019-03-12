package hiof.gr19.seat;

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
        int systemMode = input.nextInt();

        switch (systemMode){
            case 1:
                System.out.println("organizer");
                //organizerMode()
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

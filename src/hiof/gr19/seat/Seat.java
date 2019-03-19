package hiof.gr19.seat;

import java.io.Console;

public class Seat {
    public static void main(String[] args){
        User.Type user = identifyUser();


    }

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
                main(null);
                break;
        }

        return null;
    }


}

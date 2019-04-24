package hiof.gr19.seat;

import hiof.gr19.seat.console.ui.BetalingsKontrollorConsole;
import hiof.gr19.seat.console.ui.Console;
import hiof.gr19.seat.console.ui.CustomerConsole;
import hiof.gr19.seat.console.ui.OrganizerConsole;
import hiof.gr19.seat.model.User;

import static hiof.gr19.seat.console.ui.Console.identifyUser;

public class Seat {

    public static void main(String[] args){

        User.Type userType = defineUserType();
        Console console = null;

        switch (userType) {
            case Customer:
                console = new CustomerConsole();
                break;
            case Organizer:
                console = new OrganizerConsole();
                break;
            case TicketChecker:
                console = new BetalingsKontrollorConsole();
                break;
            default:
                userType = defineUserType();
                break;
        }

        console.start();
    }

    private static User.Type defineUserType(){

        User.Type userType = null;

        // This loop forces the identifyUser() method to rerun until it gets a valid User.Type
        while(userType == null)
            userType = identifyUser();

        return userType;
    }
}

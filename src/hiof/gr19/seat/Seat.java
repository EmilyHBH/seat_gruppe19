package hiof.gr19.seat;

import hiof.gr19.seat.console.ui.Console;
import hiof.gr19.seat.console.ui.CustomerConsole;
import hiof.gr19.seat.console.ui.OrganizerConsole;
import hiof.gr19.seat.model.User;

import static hiof.gr19.seat.console.ui.Console.identifyUser;

public class Seat {

    public static void main(String[] args){

        User.Type userType = null;

        // This loop forces the identifyUser() method to rerun until it gets a valid User.Type
        while(userType == null)
            userType = identifyUser();

        switch (userType) {
            case Customer:
                CustomerConsole customerConsole = new CustomerConsole();
                customerConsole.start();
                break;
            case Organizer:
                OrganizerConsole organizerConsole = new OrganizerConsole();
                organizerConsole.start();
                break;
            default:
                break;
        }
    }
}

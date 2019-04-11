package hiof.gr19.seat;

import hiof.gr19.seat.console.ui.Console;
import hiof.gr19.seat.console.ui.CustomerConsole;
import hiof.gr19.seat.console.ui.OrganizerConsole;
import org.apache.commons.lang3.ObjectUtils;

import static hiof.gr19.seat.console.ui.Console.identifyUser;
import static hiof.gr19.seat.console.ui.CustomerConsole.purchaseTicket;

public class Seat {

    public static void main(String[] args){



        User.Type userType = null;
        Console c;

        // This loop forces the identifyUser() method to rerun until it gets a valid User.Type
        while(userType == null)
            userType = identifyUser();

        switch (userType) {
            case Customer:
                c = new CustomerConsole();
                c.start();
                break;
            case Organizer:
                c = new OrganizerConsole();
                c.start();
                break;
            default:
                break;
        }
    }
}

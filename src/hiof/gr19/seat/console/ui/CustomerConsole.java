package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Arrangement;

import java.io.IOException;

public class CustomerConsole extends Console{

    public static void customerMenu(){

        java.io.Console console = System.console();
        if (console == null){
            throw new NullPointerException("No console found");
        }

        System.out.println("Choose arrangement based on ID");
        //TODO print all arrangments


        String arrangmentID = console.readLine(">");
        //TODO metode for å hente arrangement basert på id


        try {
            purchaseTicketMenu(null);

        } catch (IOException e) {
            System.out.println(e);
        }

    }


    static void purchaseTicketMenu(Arrangement arrangement) throws IOException {
        java.io.Console console = System.console();
        if (console == null){
            throw new NullPointerException("No console found");
        }

        System.out.println("How many tickets?");
        String ticketAmount = console.readLine(">");


        if (Integer.parseInt(ticketAmount) > arrangement.getMaxAttendees()){
            throw new IOException("No more tickets left");

        }

    }

}

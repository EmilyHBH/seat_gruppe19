package hiof.gr19.seat.system;

import hiof.gr19.seat.Seat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SystemKravTester {
    public static final String ENTER = System.lineSeparator();

    @Test
    public void applikasjonStarter(){
        //Krav 004

        //Går uendelig, må fikses.
        new Thread()
        {
            @Override
            public void run() {
                Seat.main(null);
            }

        }.start();

        try {
            Thread.sleep(1000);
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }

        /*assertTrue(
                ("1 = Organizer"+ENTER +
                        "2 = Customer"+ENTER +
                        "3 = Ticket Checker"+ENTER+ENTER +
                        "What are you:"+ENTER+ENTER).equals(
                        getConsoleOutput()));*/
    }
}

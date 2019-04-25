package hiof.gr19.seat.system;

import hiof.gr19.seat.Seat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SystemKravTester extends Thread{

    class PrimeThread extends Thread {

        public void run() {
            Seat.main(null);
        }

    }

    @Test
    public void test(){
        PrimeThread p = new PrimeThread();
        p.start();
        assertTrue(p.isAlive());

    }

}

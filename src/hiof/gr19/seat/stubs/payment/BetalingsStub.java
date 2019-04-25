package hiof.gr19.seat.stubs.payment;

import java.util.Random;

public class BetalingsStub {
    private int telefonnummer;
    private String navn;

    public BetalingsStub(String navn) { this.navn = navn; }

    public BetalingsStub(int telefonnummer, String navn) {
        this.telefonnummer = telefonnummer;
        this.navn = navn;
    }

    //Denne koden brukes bare for å vise at resten av programmet skal fungere som det skal
    public boolean godkjentBetaling(){


        // 50/50 om betalingen blir godkjent
        Random rand = new Random();
        int result = rand.nextInt(2);
        System.out.println(result);
        if (result == 0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }
}

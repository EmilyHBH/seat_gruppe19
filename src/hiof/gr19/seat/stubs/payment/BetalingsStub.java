package hiof.gr19.seat.stubs.payment;

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

        boolean returnVerdi = false;

        if(navn.length() % 2 == 0){
            returnVerdi = true;
        }

        return returnVerdi;
    }
}

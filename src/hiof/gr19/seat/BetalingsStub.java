package hiof.gr19.seat;

public class BetalingsStub {
    int telefonnummer;
    String navn;

    public BetalingsStub(int telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public BetalingsStub(int telefonnummer, String navn) {
        this.telefonnummer = telefonnummer;
        this.navn = navn;
    }

    public boolean godkjentBetaling(){
        //Dette er tullekode

        boolean returnVerdi = false;

        if(navn.length() % 2 == 0){
            returnVerdi = true;
        }

        return returnVerdi;
    }
}

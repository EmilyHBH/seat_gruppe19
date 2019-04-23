package hiof.gr19.seat.model;

public class Ticket {
    private int id, pris, antall;
    private String beskrivelse;

    public Ticket(int id, int pris, int antall, String beskrivelse) {
        this.id = id;
        this.pris = pris;
        this.antall = antall;
        this.beskrivelse = beskrivelse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPris() {
        return pris;
    }

    public void setPris(int pris) {
        this.pris = pris;
    }

    public int getAntall() {
        return antall;
    }

    public void setAntall(int antall) {
        this.antall = antall;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }
}

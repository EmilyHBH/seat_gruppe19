package hiof.gr19.seat.model;

public class Organizer {
    private int organizerID;
    private String organizerName;
    private String email;

    public Organizer(int organizerID, String organizerName, String email) {
        this.organizerID = organizerID;
        this.organizerName = organizerName;
        this.email = email;
    }

    public Organizer(int organizerID, String organizerName) {
        this.organizerID = organizerID;
        this.organizerName = organizerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getOrganizerID() {
        return organizerID;
    }

    public void setOrganizerID(int organizerID) {
        this.organizerID = organizerID;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    @Override
    public String toString() {
        return organizerID + " " + organizerName;
    }
}

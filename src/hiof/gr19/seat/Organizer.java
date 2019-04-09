package hiof.gr19.seat;

public class Organizer {
    private String organizerID;
    private String organizerName;
    private String email;

    public Organizer(String organizerID, String organizerName, String email) {
        this.organizerID = organizerID;
        this.organizerName = organizerName;
        this.email = email;
    }

    public Organizer(String organizerID, String organizerName) {
        this.organizerID = organizerID;
        this.organizerName = organizerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganizerID() {
        return organizerID;
    }

    public void setOrganizerID(String organizerID) {
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

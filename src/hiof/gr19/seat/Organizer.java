package hiof.gr19.seat;

public class Organizer {
    private int organizerID;
    private String organizerName;

    public Organizer(int organizerID, String organizerName) {
        this.organizerID = organizerID;
        this.organizerName = organizerName;
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


}

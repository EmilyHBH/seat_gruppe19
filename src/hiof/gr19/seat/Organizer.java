package hiof.gr19.seat;

public class Organizer {
    private String organizerID;
    private String organizerName;
    private String email;
    private String sha1Password;

    public Organizer(String organizerID, String organizerName, String email, String sha1Password) {
        this.organizerID = organizerID;
        this.organizerName = organizerName;
        this.email = email;
        this.sha1Password = sha1Password;
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

    public String getSha1Password() {
        return sha1Password;
    }

    public void setSha1Password(String sha1Password) {
        this.sha1Password = sha1Password;
    }

    @Override
    public String toString() {
        return organizerID + " " + organizerName;
    }
}

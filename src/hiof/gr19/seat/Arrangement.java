package hiof.gr19.seat;

import java.util.Date;

public class Arrangement {
    private String arrangementID;
    private String arrangmentTitle;
    private String arrangmentDescription;
    private Date arragmentDate;
    private String location;
    private Organizer organizer;
    private int maxAttendees;

    public Arrangement(String arrangementID, String arrangmentTitle, String arrangmentDescription, Date arragmentDate, Organizer organizer, int maxattendees, String location) {
        this.arrangementID = arrangementID;
        this.arrangmentTitle = arrangmentTitle;
        this.arrangmentDescription = arrangmentDescription;
        this.arragmentDate = arragmentDate;
        this.organizer = organizer;
        this.maxAttendees = maxattendees;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTotalInformation() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID: " + arrangementID);
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("Title: " + arrangmentTitle);
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("Description: " + arrangmentDescription);
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("Date: " + arragmentDate);
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("Organizer: " + organizer);
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("People amount: " + maxAttendees);
        stringBuilder.append(System.lineSeparator());

        return stringBuilder.toString();
    }

    public String getArrangementID() {
        return arrangementID;
    }

    public void setArrangementID(String arrangementID) {
        this.arrangementID = arrangementID;
    }

    public String getArrangmentTitle() {
        return arrangmentTitle;
    }

    public void setArrangmentTitle(String arrangmentTitle) {
        this.arrangmentTitle = arrangmentTitle;
    }

    public String getArrangmentDescription() {
        return arrangmentDescription;
    }

    public void setArrangmentDescription(String arrangmentDescription) {
        this.arrangmentDescription = arrangmentDescription;
    }

    public Date getArragmentDate() {
        return arragmentDate;
    }

    public void setArragmentDate(Date arragmentDate) {
        this.arragmentDate = arragmentDate;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public int getMaxAttendees() {
        return maxAttendees;
    }

    public void setMaxAttendees(int maxAttendees) {
        this.maxAttendees = maxAttendees;
    }

    @Override
    public String toString() {
        return arrangmentTitle + arragmentDate.toString();
    }
}

package hiof.gr19.seat;

import java.util.Date;

public class Arrangement {
    private String arrangementID;
    private String arrangmentTitle;
    private String arrangmentDescription;
    private Date arragmentDate;
    private Organizer organizer;
    private int peopleAmount;

    public Arrangement(String arrangementID, String arrangmentTitle, String arrangmentDescription, Date arragmentDate, Organizer organizer, int peopleAmount) {
        this.arrangementID = arrangementID;
        this.arrangmentTitle = arrangmentTitle;
        this.arrangmentDescription = arrangmentDescription;
        this.arragmentDate = arragmentDate;
        this.organizer = organizer;
        this.peopleAmount = peopleAmount;
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

    public int getPeopleAmount() {
        return peopleAmount;
    }

    public void setPeopleAmount(int peopleAmount) {
        this.peopleAmount = peopleAmount;
    }
}

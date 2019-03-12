package hiof.gr19.seat;

public class Ticket {
    private String id;
    private Person owner;
    private int validForPerson;
    private Arrangement arrangement;

    public Ticket(String id, Person owner, int validForPerson, Arrangement arrangement) {
        this.id = id;
        this.owner = owner;
        this.validForPerson = validForPerson;
        this.arrangement = arrangement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public int getValidForPerson() {
        return validForPerson;
    }

    public void setValidForPerson(int validForPerson) {
        this.validForPerson = validForPerson;
    }

    public Arrangement getArrangement() {
        return arrangement;
    }

    public void setArrangement(Arrangement arrangement) {
        this.arrangement = arrangement;
    }
}

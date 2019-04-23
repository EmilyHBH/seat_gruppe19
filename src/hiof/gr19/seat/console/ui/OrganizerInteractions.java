package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Arrangement;
import hiof.gr19.seat.Database;
import hiof.gr19.seat.Organizer;

import java.sql.SQLException;
import java.util.ArrayList;

abstract class OrganizerInteractions {

    Organizer user;
    Database db = new Database();

    ArrayList<String> menuListOfFunctions = new ArrayList<>(){{
        add("Create event");
        add("Add an additional type of ticket to an event");
        add("Change an events information");
        add("Exit");
    }};
    ArrayList<String> eventInfoCapableOfChange = new ArrayList<>(){{
        add("Name");
        add("Description");
        add("Date");
        add("I'm done changing");
    }};

    abstract boolean returningOrganizer();
    abstract void viewMenu();
    abstract void createArrangement();
    abstract void changeEventInfo(Arrangement arrangement);
    abstract void defineMultipleTickets(Arrangement arrangement);
    abstract String getOrganizerNameOfExisting();
    abstract String[] getRegistrationInfo();

    void loginOrRegister(){

        boolean hasExistingUser = returningOrganizer();

        if(hasExistingUser)
            user = login(getOrganizerNameOfExisting());
        else
            user = register(getRegistrationInfo());
    }
    void updateDBonEventInfoChanged(Arrangement arrangement){
        try {
            db.changeEventInfo(arrangement.getArrangementID(), arrangement.getArrangmentTitle(), arrangement.getArrangmentDescription(), arrangement.getArragmentDateInStringFormat());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    void addEventToDb(Arrangement arrangement){
        try {
            db.createEvent(arrangement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    void addTicketsToEvent(int arrangementID, int antall, int pris, String bilettBeskrivelse){
        try {
            db.defineArrangementTickets(arrangementID,antall,pris, bilettBeskrivelse);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private Organizer login(String name){
        try {
            boolean organizerStatus = db.checkForOrganizer(name);
            if (organizerStatus){
                System.out.println("Welcome " + name +"\n");
                return db.getOrganizerByName(name);
            }else {
                System.out.println(name + " Is not registered\n");
                login(getOrganizerNameOfExisting());
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Organizer register(String[] nameAndEmail){

        try {
            boolean organizerStatus = db.checkForOrganizer(nameAndEmail[0]);
            if (organizerStatus){
                System.out.println("This organization name already exists, choose another");
                register(getRegistrationInfo());
            } else {
                String email = nameAndEmail[1];

                // input new organizer to db
                try {
                    return db.addOrganizer(nameAndEmail[0], email);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}

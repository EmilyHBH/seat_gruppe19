package hiof.gr19.seat;

import java.sql.*;
import java.util.ArrayList;

import static hiof.gr19.seat.console.ui.Console.parseDate;

public class Database {

    private static Connection dbCon;

    private void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        dbCon = DriverManager.getConnection("jdbc:sqlite:tempDB.db");
    }

    // Retrieve data
    private Organizer getOrganizerById(int id) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement statement = dbCon.prepareStatement("SELECT * FROM arrangor WHERE id = ?");
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();

        if(result.next()){
            // TODO:: sjekk funker
            return new Organizer(result.getString("id"), result.getString("navn"), result.getString("email"));
        }

        return null;
    }
    public Arrangement getEventById(int id) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement statement = dbCon.prepareStatement("SELECT * FROM arrangement WHERE id = ?");
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();

        if(result.next()) {
            Organizer eventHolder = getOrganizerById(result.getInt("arrangor_fk"));
            java.util.Date date = parseDate(result.getString("dato"));

            // TODO:: sjekk funker
            return new Arrangement(result.getString("id"), result.getString("navn"), result.getString("beskrivelse"), date, eventHolder, result.getInt("total_biletter"), result.getString("addresse"));
        }

        return null;
    }
    public boolean checkForOrganizer(String organizer) throws SQLException, ClassNotFoundException{
        if(dbCon == null){
            getConnection();
        }
        String query = "SELECT COUNT(*) FROM arrangor WHERE navn = ?";
        PreparedStatement statement = dbCon.prepareStatement(query);
        statement.setString(1,organizer);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();

    }
    public ArrayList<Arrangement> displayEvents() throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        Statement state = dbCon.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM arrangement;");

        ArrayList<Arrangement> events = new ArrayList<>();

        while(result.next()){
            Organizer eventHolder = getOrganizerById(result.getInt("arrangor_fk"));
            java.util.Date date = parseDate(result.getString("dato"));

            // TODO:: sjekk funker
            events.add(new Arrangement(result.getString("id"), result.getString("navn"), result.getString("beskrivelse"), date, eventHolder, result.getInt("total_biletter"), result.getString("addresse")));
        }

        return events;
    }
    public ResultSet displayAnEventsTickets(int arrangementId) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        Statement state = dbCon.createStatement();
        return state.executeQuery("SELECT * FROM arrangements_biletter WHERE arrangementId = " + arrangementId + ";");
    }
    public ResultSet displayAllUsersTickets(int kundeId) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        Statement state = dbCon.createStatement();
        return state.executeQuery("SELECT * FROM kundes_billetter WHERE kundeId = " + kundeId + ";");
    }

    // Insert data
    public void addUser(Person person) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement prep = dbCon.prepareStatement("INSERT INTO kunde(forNavn,etterNavn,kontaktinfo,alder) values(?,?,?,?)");
        prep.setString(1,person.getFirstName());
        prep.setString(2, person.getLastName());
        prep.setString(3, person.getEmailAddress());
        prep.setInt(4, person.getAge());
        prep.execute();
    }
    public void addOrganizer(Organizer organizer) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        // TODO:: sjekk funker
        PreparedStatement prep = dbCon.prepareStatement("INSERT INTO arrangor(navn,email) values(?,?)");
        prep.setString(1,organizer.getOrganizerName());
        prep.setString(2, organizer.getEmail());
        prep.execute();
    }
    public void createEvent(Arrangement arrangement) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        // TODO:: sjekk funker
        PreparedStatement prep = dbCon.prepareStatement("INSERT INTO arrangement(navn,beskrivelse,dato,arrangor_fk, addresse, total_biletter) values(?,?,?,?,?,?)");
        prep.setString(1,arrangement.getArrangmentTitle());
        prep.setString(2, arrangement.getArrangmentDescription());
        prep.setString(3, String.valueOf(arrangement.getArragmentDate()));
        prep.setInt(4, Integer.parseInt(arrangement.getOrganizer().getOrganizerID()));
        prep.setString(5, arrangement.getLocation());
        prep.setInt(6, arrangement.getMaxAttendees());
        prep.execute();
    }
    public void userPurchasedTickets(int kundeId, int billettId, int antall) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement prep = dbCon.prepareStatement("INSERT INTO kundes_billetter values(?,?,?)");
        prep.setInt(1,kundeId);
        prep.setInt(2, billettId);
        prep.setInt(3, antall);
        prep.execute();
    }

    // Update data
    public void changeEmailOfOrganizer(Organizer organizer, String newEmail) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement prep = dbCon.prepareStatement("UPDATE arrangor SET email = ? WHERE id = " + organizer.getOrganizerID());
        prep.setString(1,newEmail);
        prep.execute();
    }
    public void changeEventInfo(int id, String name, String description, String newDate) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement prep = dbCon.prepareStatement("UPDATE arrangement SET dato = ?, navn = ?, beskrivelse = ? WHERE id = " + id);
        prep.setString(1,newDate);
        prep.setString(2,name);
        prep.setString(3,description);
        prep.execute();
    }

    // Delete data
    public void cancelAUsersTicket(int kundeId, int billettId) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement prep = dbCon.prepareStatement("DELETE FROM kundes_billetter WHERE kundeId = ? AND billettId = ?");
        prep.setInt(1,kundeId);
        prep.setInt(2,billettId);
        prep.execute();
    }
    public void cancelEvent(int id) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement prep = dbCon.prepareStatement("DELETE FROM arrangement WHERE id = ?");
        prep.setInt(1,id);
        prep.execute();
    }
}

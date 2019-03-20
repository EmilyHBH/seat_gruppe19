package hiof.gr19.seat;

import java.sql.*;

public class Database {

    private static Connection dbCon;

    private void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        dbCon = DriverManager.getConnection("jdbc:sqlite:tempDB.db");
    }

    // Retrieve data
    public ResultSet displayUsers() throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        Statement state = dbCon.createStatement();
        return state.executeQuery("SELECT * FROM kunde;");
    }
    public ResultSet displayEvents() throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        Statement state = dbCon.createStatement();
        return state.executeQuery("SELECT * FROM arrangement;");
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
    public void addOrganizer(String navn, String email) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement prep = dbCon.prepareStatement("INSERT INTO arrangor(navn,email) values(?,?)");
        prep.setString(1,navn);
        prep.setString(2, email);
        prep.execute();
    }
    public void createEvent(Arrangement arrangement/*String navn, String beskrivelse, String dato, int arrangor_fk*/) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement prep = dbCon.prepareStatement("INSERT INTO arrangement(navn,beskrivelse,dato,arrangor_fk) values(?,?,?,?)");
        prep.setString(1,arrangement.getArrangmentTitle());
        prep.setString(2, arrangement.getArrangmentDescription());
        prep.setString(3, String.valueOf(arrangement.getArragmentDate()));
        prep.setInt(4, arrangement.getOrganizer().getOrganizerID());
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

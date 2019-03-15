package hiof.gr19.seat;

import java.sql.*;

public class Database {

    private static Connection dbCon;

    private void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        dbCon = DriverManager.getConnection("jdbc:sqlite:tempDB.db");
    }

    // Retrieve data
    ResultSet displayUsers() throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        Statement state = dbCon.createStatement();
        return state.executeQuery("SELECT * FROM kunde;");
    }
    ResultSet displayEvents() throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        Statement state = dbCon.createStatement();
        return state.executeQuery("SELECT * FROM arrangement;");
    }
    ResultSet displayAnEventsTickets(int arrangementId) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        Statement state = dbCon.createStatement();
        return state.executeQuery("SELECT * FROM arrangements_biletter WHERE arrangementId = " + arrangementId + ";");
    }
    ResultSet displayAllUsersTickets(int kundeId) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        Statement state = dbCon.createStatement();
        return state.executeQuery("SELECT * FROM kundes_billetter WHERE kundeId = " + kundeId + ";");
    }


    // Insert data
    public void addUser(String forNavn, String etterNavn, String kontaktinfo, int alder) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement prep = dbCon.prepareStatement("INSERT INTO kunde(forNavn,etterNavn,kontaktinfo,alder) values(?,?,?,?)");
        prep.setString(1,forNavn);
        prep.setString(2, etterNavn);
        prep.setString(3, kontaktinfo);
        prep.setInt(4, alder);
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
    public void createEvent(String navn, String beskrivelse, String dato, int arrangor_fk) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement prep = dbCon.prepareStatement("INSERT INTO arrangement(navn,beskrivelse,dato,arrangor_fk) values(?,?,?,?)");
        prep.setString(1,navn);
        prep.setString(2, beskrivelse);
        prep.setString(3, dato);
        prep.setInt(4, arrangor_fk);
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
}

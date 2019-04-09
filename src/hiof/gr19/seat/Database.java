package hiof.gr19.seat;

import java.sql.*;

public class Database {

    private static Connection dbCon;
    private static boolean hasData = false;

    private void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        dbCon = DriverManager.getConnection("jdbc:sqlite:tempDB.db");
        initialise();
    }

    // Creates db tables and populates them with dummy data if they don't already exist
    private void initialise() throws SQLException {
        if(!hasData){
            hasData = true;
            Statement statement = dbCon.createStatement();

            ResultSet kundeTable = statement.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name='kunde'");
            if(!kundeTable.next()){
                Statement createKundeTable = dbCon.createStatement();
                createKundeTable.execute("CREATE TABLE kunde (" +
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "forNavn TEXT NOT NULL, " +
                        "etterNavn TEXT NOT NULL, " +
                        "kontaktinfo TEXT NOT NULL, " +
                        "alder INTEGER NOT NULL " +
                        ")");

                // Dummy data
                createKundeTable.execute("INSERT INTO kunde values(?, \"Mickey\", \"Mouse\", \"Heyooo\", \"20\")");
                createKundeTable.execute("INSERT INTO kunde values(?, \"Donald\", \"Duck\", \"19283746\", \"15\")");
                createKundeTable.execute("INSERT INTO kunde values(?, \"Goofy\", \"Goof\", \"12345678\", \"10\")");
            }

            ResultSet billettTable = statement.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name='bilett'");
            if(!billettTable.next()){
                Statement createBillettTable = dbCon.createStatement();
                createBillettTable.execute("CREATE TABLE bilett (" +
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "pris INTEGER NOT NULL " +
                        ")");
            }

            ResultSet billettKundeTable = statement.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name='kundes_billetter'");
            if(!billettKundeTable.next()){
                Statement createBillettKundeTable = dbCon.createStatement();
                createBillettKundeTable.execute("CREATE TABLE kundes_billetter (" +
                        "kundeId INTEGER NOT NULL, " +
                        "billettId INTEGER NOT NULL, " +
                        "antall INTEGER NOT NULL, " +
                        "FOREIGN KEY(kundeId) REFERENCES kunde(id) on delete cascade on update cascade, " +
                        "PRIMARY KEY(kundeId,billettId), " +
                        "FOREIGN KEY(billettId) REFERENCES bilett(id) on delete cascade on update cascade " +
                        ")");
            }

            ResultSet arrangorTable = statement.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name='arrangor'");
            if(!arrangorTable.next()){
                Statement createArrangorTable = dbCon.createStatement();
                createArrangorTable.execute("CREATE TABLE arrangor ( " +
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "navn TEXT NOT NULL, " +
                        "email TEXT NOT NULL " +
                        ")");

                // Dummy data
                createArrangorTable.execute("INSERT INTO arrangor values(?, \"OrgStarter\", \"newEmail@gmail.com\")");
            }

            ResultSet arrangementTable = statement.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name='arrangement'");
            if(!arrangementTable.next()){
                Statement createArrangementTable = dbCon.createStatement();
                createArrangementTable.execute("CREATE TABLE arrangement (" +
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "navn TEXT NOT NULL, " +
                        "beskrivelse TEXT NOT NULL, " +
                        "dato TEXT NOT NULL, " +
                        "aldersgrense INTEGER, " +
                        "addresse TEXT NOT NULL, " +
                        "arrangor_fk INTEGER NOT NULL, " +
                        "total_biletter INTEGER NOT NULL, " +
                        "FOREIGN KEY(arrangor_fk) REFERENCES arrangor(id) on delete cascade on update cascade " +
                        ")");

                // Dummy data TODO:: sjekk dato
                createArrangementTable.execute("INSERT INTO arrangement values(?, \"Fyre Festival\", \"Beskrivelse.........\", \"09-05-2019\", \"20\",\"Remmen 990\", \"1\", \"200\")");
            }

            ResultSet arrangementsBiletterTable = statement.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name='arrangements_biletter'");
            if(!arrangementsBiletterTable.next()){
                Statement createArrangementsBiletterTable = dbCon.createStatement();
                createArrangementsBiletterTable.execute("CREATE TABLE arrangements_biletter (" +
                        "arrangementId INTEGER NOT NULL, " +
                        "billettId INTEGER NOT NULL, " +
                        "antall INTEGER NOT NULL, " +
                        "PRIMARY KEY(arrangementId, billettId), " +
                        "FOREIGN KEY(arrangementId) REFERENCES arrangement(id) on delete cascade on update cascade, " +
                        "FOREIGN KEY(billettId) REFERENCES bilett(id) on delete cascade on update cascade " +
                        ")");
            }
        }
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
        prep.setInt(4, Integer.parseInt(arrangement.getOrganizer().getOrganizerID()));
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

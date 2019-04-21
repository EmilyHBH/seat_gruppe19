package hiof.gr19.seat;

import hiof.gr19.seat.model.Arrangement;
import hiof.gr19.seat.model.Organizer;
import hiof.gr19.seat.model.Person;
import hiof.gr19.seat.model.Ticket;

import java.sql.*;
import java.util.ArrayList;

import static hiof.gr19.seat.console.ui.Console.parseDate;

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
                        "pris INTEGER NOT NULL," +
                        "beskrivelse TEXT " +
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
    public Organizer getOrganizerByName(String navn) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement statement = dbCon.prepareStatement("SELECT * FROM arrangor WHERE navn = ?");
        statement.setString(1, navn);
        ResultSet result = statement.executeQuery();

        if(result.next())
            return new Organizer(result.getInt("id"), result.getString("navn"), result.getString("email"));

        return null;
    }
    public Arrangement getEventById(int id) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement statement = dbCon.prepareStatement("SELECT arrangement.*, arrangor.navn as arnavn FROM arrangement INNER JOIN arrangor ON arrangement.arrangor_fk = arrangor.id WHERE arrangement.id = ?");
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();

        if(result.next()) {
            Organizer eventHolder = getOrganizerByName(result.getString("arnavn"));
            java.util.Date date = parseDate(result.getString("dato"));
            ArrayList<Ticket> tickets = getAnEventsTickets(result.getInt("id"));

            return new Arrangement(result.getInt("id"), result.getString("navn"), result.getString("beskrivelse"), date, eventHolder, result.getInt("total_biletter"), result.getString("addresse"), tickets);
        }

        return null;
    }
    public boolean checkForOrganizer(String organizer) throws SQLException, ClassNotFoundException{
        if(dbCon == null){
            getConnection();
        }
        String query = "SELECT COUNT(*) as occurencesOfName FROM arrangor WHERE navn = ?";
        PreparedStatement statement = dbCon.prepareStatement(query);
        statement.setString(1,organizer);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next())
            return resultSet.getInt("occurencesOfName") >= 1;
        else
            return false;
    }
    public ArrayList<Arrangement> getEvents() throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        Statement state = dbCon.createStatement();
        ResultSet result = state.executeQuery("SELECT arrangement.*, arrangor.navn as arnavn FROM arrangement INNER JOIN arrangor ON arrangement.arrangor_fk = arrangor.id;");

        ArrayList<Arrangement> events = new ArrayList<>();

        while(result.next()){
            Organizer eventHolder = getOrganizerByName(result.getString("arnavn"));
            java.util.Date date = parseDate(result.getString("dato"));
            ArrayList<Ticket> tickets = getAnEventsTickets(result.getInt("id"));

            events.add(new Arrangement(result.getInt("id"), result.getString("navn"), result.getString("beskrivelse"), date, eventHolder, result.getInt("total_biletter"), result.getString("addresse"), tickets));
        }

        return events;
    }
    public ArrayList<Arrangement> getEventsByOrganizer(int organizerId) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement state = dbCon.prepareStatement("SELECT arrangement.*, arrangor.navn as arnavn  FROM arrangement INNER JOIN arrangor ON arrangement.arrangor_fk = arrangor.id WHERE arrangor_fk = ?;");
        state.setInt(1, organizerId);
        ResultSet result = state.executeQuery();

        ArrayList<Arrangement> events = new ArrayList<>();

        while(result.next()){
            Organizer eventHolder = getOrganizerByName(result.getString("arnavn"));
            java.util.Date date = parseDate(result.getString("dato"));
            ArrayList<Ticket> tickets = getAnEventsTickets(result.getInt("id"));

            events.add(new Arrangement(result.getInt("id"), result.getString("navn"), result.getString("beskrivelse"), date, eventHolder, result.getInt("total_biletter"), result.getString("addresse"), tickets));
        }

        return events;
    }
    public ArrayList<Ticket> getAnEventsTickets(int arrangementId) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        Statement state = dbCon.createStatement();
        ResultSet result = state.executeQuery("SELECT bilett.*, arrangements_biletter.antall FROM bilett INNER JOIN arrangements_biletter ON billettId = bilett.id WHERE arrangementId = " + arrangementId + ";");

        ArrayList<Ticket> eventsTickets = new ArrayList<>();

        while(result.next())
            eventsTickets.add(new Ticket(result.getInt("id"), result.getInt("pris"), result.getInt("antall"), result.getString("beskrivelse")));

        return eventsTickets;

    }
    public ArrayList<Ticket> getAllUsersTickets(int kundeId) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        Statement state = dbCon.createStatement();
        ResultSet result = state.executeQuery("SELECT bilett.*, kundes_biletter.antall FROM bilett INNER JOIN kundes_biletter ON billettId = bilett.id WHERE kundeId = " + kundeId + ";");

        ArrayList<Ticket> usersTickets = new ArrayList<>();

        while(result.next())
            usersTickets.add(new Ticket(result.getInt("id"), result.getInt("pris"), result.getInt("antall"), result.getString("beskrivelse")));

        return usersTickets;
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
    public Organizer addOrganizer(String name, String email) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement prep = dbCon.prepareStatement("INSERT INTO arrangor(navn,email) values(?,?)");
        prep.setString(1,name);
        prep.setString(2, email);
        prep.execute();

        try (ResultSet gkeys = prep.getGeneratedKeys()) {
            if (gkeys.next())
                return new Organizer(gkeys.getInt(1), name, email);
        }

        return null;
    }
    public void createEvent(Arrangement arrangement) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement prep = dbCon.prepareStatement("INSERT INTO arrangement(navn,beskrivelse,dato,arrangor_fk, addresse, total_biletter) values(?,?,?,?,?,?)");
        prep.setString(1,arrangement.getArrangmentTitle());
        prep.setString(2, arrangement.getArrangmentDescription());
        prep.setString(3, arrangement.getArragmentDateInStringFormat());
        prep.setInt(4, arrangement.getOrganizer().getOrganizerID());

        prep.setString(5, arrangement.getLocation());
        prep.setInt(6, arrangement.getMaxAttendees());
        prep.execute();

        try (ResultSet gkeys = prep.getGeneratedKeys()) {
            if (gkeys.next())
                arrangement.setArrangementID(gkeys.getInt(1));
        }
    }
    public void registeredUserPurchasedTickets(int kundeId, int billettId, int antall) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement prep = dbCon.prepareStatement("INSERT INTO kundes_billetter values(?,?,?)");
        prep.setInt(1,kundeId);
        prep.setInt(2, billettId);
        prep.setInt(3, antall);
        prep.execute();
    }
    public void defineArrangementTickets(int arrangementId, int antall, int pris, String beskrivelse) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        // insert ticket into tickets table
        PreparedStatement prep1 = dbCon.prepareStatement("INSERT INTO bilett(pris, beskrivelse) values(?,?)");
        prep1.setInt(1,pris);
        prep1.setString(2,beskrivelse);
        prep1.execute();

        try (ResultSet gkeys = prep1.getGeneratedKeys()) {
            if (gkeys.next()) {
                int billettId = gkeys.getInt(1);

                // link the ticket with the arrangement
                PreparedStatement prep2 = dbCon.prepareStatement("INSERT INTO arrangements_biletter values(?,?,?)");
                prep2.setInt(1, arrangementId);
                prep2.setInt(2, billettId);
                prep2.setInt(3, antall);
                prep2.execute();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Update data
    public void changeEmailOfOrganizer(Organizer organizer, String newEmail) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement prep = dbCon.prepareStatement("UPDATE arrangor SET email = ? WHERE id = " + organizer.getOrganizerID());
        prep.setString(1,newEmail);
        prep.execute();

        organizer.setEmail(newEmail);
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
    public void ticketsHaveBeenPurchasedFromEvent(int ticketId, int amountOfTickets) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement prep = dbCon.prepareStatement("UPDATE arrangements_biletter SET antall = antall - ? WHERE billettId = " + ticketId);
        prep.setInt(1,amountOfTickets);
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

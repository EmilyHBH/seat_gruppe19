package hiof.gr19.seat;

import java.sql.*;

public class Database {

    private static Connection dbCon;

    private void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        dbCon = DriverManager.getConnection("jdbc:sqlite:tempDB.db");
    }

    ResultSet displayUsers() throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        Statement state = dbCon.createStatement();
        return state.executeQuery("SELECT * FROM kunde");
    }

    public void addUser(String forNavn, String etterNavn) throws SQLException, ClassNotFoundException {
        if(dbCon == null)
            getConnection();

        PreparedStatement prep = dbCon.prepareStatement("INSERT INTO kunde values(?,?,?)");
        prep.setString(2,forNavn);
        prep.setString(3, etterNavn);
        prep.execute();
    }
}

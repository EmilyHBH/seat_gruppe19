package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    Database db;

    @BeforeEach
    public void setup(){
        db = new Database();
    }

    @Test
    public void testDatabaseConnectionDoesntExistYet(){

        // Krav 005

        assertNull(db.dbCon);
    }

    @Test
    public void testDatabaseConnectionConnects() throws SQLException, ClassNotFoundException {

        // Krav 005

        db.getEvents();
        assertNotNull(db.dbCon);
    }

}

package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Database;
import hiof.gr19.seat.model.Arrangement;
import hiof.gr19.seat.model.Organizer;
import hiof.gr19.seat.model.Ticket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleTest {
	public static final String ENTER = System.lineSeparator();

	//For å simulere bruker input
	private final InputStream systemIn = System.in;
	private final PrintStream systemOut = System.out;
	private ByteArrayInputStream forcedInput;
	private ByteArrayOutputStream testOut;

	public void provideInput(String data) {
		forcedInput = new ByteArrayInputStream(data.getBytes());
		System.setIn(forcedInput);
	}

	public String getConsoleOutput(){
		return testOut.toString();
	}

	@BeforeEach
	public void setupOutput(){
		testOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOut));
	}

	@AfterEach
	public void restoreSystemInput(){
		System.setIn(systemIn);
		System.setOut(systemOut);
	}

	@BeforeAll
	public void lagTestData(){
		Database db = new Database();


		try {
			//Organizer testorg = new Organizer("")
			db.addOrganizer("testorg", "testorg@testmail.com");
			db.createEvent(new Arrangement(
					-1,
					"test arragement",
					"tester arrangement",
					new Date(),
					db.getOrganizerByName("testorg"),
					400,
					"Remmen",
					new ArrayList<>(){{
						new Ticket(
							-1,
							233,
							150,
							"test billett");
						new Ticket(
							-1,
							500,
							250,
							"billett tester");
					}
				}
			));

		}catch (SQLException | ClassNotFoundException ex){
			ex.printStackTrace();
		}

	}
}
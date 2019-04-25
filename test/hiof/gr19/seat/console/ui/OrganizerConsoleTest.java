package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Database;
import hiof.gr19.seat.Seat;
import hiof.gr19.seat.model.Arrangement;
import hiof.gr19.seat.model.Organizer;
import hiof.gr19.seat.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class OrganizerConsoleTest extends ConsoleTest{

	OrganizerConsole oc;

	@BeforeEach
	public void setup(){
		oc = new OrganizerConsole();
		//oc.testStart();
	}

	@Test
	public void loggetInnSomArrangor(){
		//Krav 006

		//Denne testen krever at det er en arrangor i databasen med navnet "OrgStarter", den skal alltid finnes pga. db initialise populerer med dummy data

		provideInput("OrgStarter"+ENTER);
		Organizer organizer = oc.organizerLogin();

		assertEquals("OrgStarter",organizer.getOrganizerName());

	}


	@Test
	public void registrerNyArrangor(){
		//Krav 007

		String testNavn = "test";
		String madeUpEmail = "email@email.email";

		Database db = new Database();

		try {
			db.addOrganizer(testNavn,madeUpEmail);
			assertEquals(testNavn, db.getOrganizerByName(testNavn).getOrganizerName());
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void lagArrangementTest() throws SQLException, ClassNotFoundException {

		Database db = new Database();

		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		tickets.add(new Ticket(-1,100,25,"Test billett"));

		Arrangement arrangementToAdd = new Arrangement(
				-1,
				"name",
				"description",
				new Date(2019,12,25),
				db.getOrganizerByName("OrgStarter"),
				150,
				"this place",
				tickets
		);

		db.createEvent(arrangementToAdd);

		assertEquals(arrangementToAdd.getArrangmentTitle(), db.getEventById(arrangementToAdd.getArrangementID()).getArrangmentTitle());

	}

	@Test
	public void endreArrangementDataIDB() throws SQLException, ClassNotFoundException {

		// Krav 009

		Database db = new Database();

		int arrangementId = 1;	// Fyre Festival

		String randomBeskrivelse = buildRandomString(15);

		db.changeEventInfo(arrangementId, "Fyre Festival", randomBeskrivelse, "05-01-2019");

		assertEquals(randomBeskrivelse, db.getEventById(1).getArrangmentDescription());

	}
}
package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.model.Organizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		//Krav 003

		//Denne testen krever at det er en arrangor i databasen med navnet "OrgStarter", den skal alltid finnes pga. db initialise populerer med dummy data

		provideInput("OrgStarter" + ENTER);
		Organizer organizer = oc.organizerLogin();

		assertEquals("OrgStarter",organizer.getOrganizerName());

	}

}
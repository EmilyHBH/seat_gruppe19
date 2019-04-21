package hiof.gr19.seat.console.ui;

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

		//Denne testen krever at det er en arrangor i databasen med navnet "testorg"

		provideInput("testorg" + ENTER);
		oc.organizerLogin();

	}

}
package hiof.gr19.seat.console.ui;

import hiof.gr19.seat.Seat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleTest {
	public static final String ENTER = System.lineSeparator();

	//For å simulere bruker input
	private final InputStream systemIn = System.in;
	private final PrintStream systemOut = System.out;
	private ByteArrayInputStream forcedInput;
	private ByteArrayOutputStream testOut;

	//Denne metoden simulerer consol input til scanner.
	public void provideInput(String data) {
		forcedInput = new ByteArrayInputStream(data.getBytes());
		System.setIn(forcedInput);
	}

	//Denne metoden henter consol output.
	public String getConsoleOutput(){
		return testOut.toString();
	}

	public String buildRandomString(int length){
		byte[] array = new byte[length]; 	// length is bounded by length
		new Random().nextBytes(array);

		return new String(array, Charset.forName("UTF-8"));
	}

	@BeforeEach
	public void setupOutput(){
		testOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOut));
	}

	//@AfterEach
	public void restoreSystemInput() {
		System.setIn(systemIn);
		System.setOut(systemOut);

	}

	/*
	@Test
	public void applikasjonStarter(){
		//Krav 004

		//Går uendelig, må fikses.
		new Thread()
		{
			@Override
			public void run() {
				Seat.main(null);
				try {
					Thread.sleep(500);
				}catch (InterruptedException ex){
					ex.printStackTrace();
				}
				provideInput("3");
			}

		}.start();

		try {
			Thread.sleep(1000);
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}

		assertTrue(
				("1 = Organizer"+ENTER +
						"2 = Customer"+ENTER +
						"3 = Ticket Checker"+ENTER+ENTER +
						"What are you:"+ENTER+ENTER).equals(
						getConsoleOutput()));
	}*/

}
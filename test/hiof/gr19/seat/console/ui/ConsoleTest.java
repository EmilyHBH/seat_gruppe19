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

//husk å kjøre 1 test om gangen
class ConsoleTest {
	public static final String ENTER = System.lineSeparator();

	//For å simulere bruker input
	private final InputStream systemIn = System.in;
	private final PrintStream systemOut = System.out;
	private ByteArrayInputStream forcedInput;
	private ByteArrayOutputStream testOut;

	CustomerConsole cc;

	@BeforeEach
	public void setup(){
		cc = new CustomerConsole();
	}


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

	@AfterEach
	public void restoreSystemInput() {
		System.setIn(systemIn);
		System.setOut(systemOut);
	}

}
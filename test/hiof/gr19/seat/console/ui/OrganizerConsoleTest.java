package hiof.gr19.seat.console.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class OrganizerConsoleTest {

    //For å simulere bruker input
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayInputStream forcedInput;
    private ByteArrayOutputStream testOut;

    OrganizerConsole oc;

    @BeforeEach
    public void setup(){
        oc = new OrganizerConsole();
    }

    /*
    @Test
    public void loginTest(){

    }*/

    private void provideInput(String data) {
        forcedInput = new ByteArrayInputStream(data.getBytes());
        System.setIn(forcedInput);
    }

    private String getConsoleOutput(){
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

}

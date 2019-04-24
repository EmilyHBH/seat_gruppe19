package hiof.gr19.seat.console.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputValidatorTests extends ConsoleTest{

    @Test
    public void inputAvInt(){

        // Krav 020

        provideInput("123");

        int input = InputValidator.validateIntInput("q");

        assertTrue(Integer.class.isInstance(input));
    }

    @Test
    public void inputAvString(){

        // Krav 021

        provideInput("Stringy string");

        String input = InputValidator.validateStringInput("q");

        assertTrue(String.class.isInstance(input));
    }

}

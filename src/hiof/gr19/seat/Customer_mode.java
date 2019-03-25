package hiof.gr19.seat;

import java.util.ArrayList;
import java.util.Scanner;

public class Customer_mode {

    private Scanner input;

    public Customer_mode(Scanner input) { this.input = input; }

    public void start () {

        Console.inform("Customer mode");

        ArrayList<Arrangement> arrangements = OrganizerMode.placeholderArrangementDB();

        Console.selectFromList(arrangements);
        Console.promptInput("Select arrangement: ");
        int alternativ = Integer.parseInt(input.nextLine());

        Console.promptInput("Type name: ");
        String name = input.nextLine();

        Console.promptInput("and confirmation method:");
        int alternativ2 = Integer.parseInt(input.nextLine());

        switch (alternativ2) {
            case 1:
                Console.promptInput("Epost");
                String epost = input.nextLine();
                break;
            case 2:
                Console.inform("Print");
                break;
        }

        Console.inform("Gjør klar til betaling med Vipps(tm): "); //trenger tm ascii kode
        Console.promptInput("Ditt telefonnummer: ");
        int telefonnummer = Integer.parseInt(input.nextLine());

        BetalingsStub betaling = new BetalingsStub(telefonnummer, name);
        boolean betalingGodkent = betaling.godkjentBetaling();

        Console.inform("Betalingen var: " + betalingGodkent);
    }
}

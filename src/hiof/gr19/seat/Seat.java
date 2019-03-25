package hiof.gr19.seat;

import javax.xml.crypto.Data;
import java.io.Console;
import java.util.ArrayList;
import java.util.Date;

import static hiof.gr19.seat.ConsoleInterface.identifyUser;
import static hiof.gr19.seat.ConsoleInterface.organizerMenu;

public class Seat {
    public static void main(String[] args){

        ArrayList<Arrangement> arrangements = placeholderArrangementDB() ;
        User.Type user = identifyUser();

        ConsoleInterface x = new ConsoleInterface();
        organizerMenu();
        //x.printArrangements(arrangements);


    }


    private static ArrayList<Arrangement> placeholderArrangementDB (){

        // SKAL BORT
        Date date = new Date();
        Organizer organizer = new Organizer("testOrgId", "Test org inc");
        ArrayList<Arrangement> arrayList =  new ArrayList<>();
        Arrangement arrangement1 = new Arrangement("TestArrangID1", "TestArrangNavn1", "TestArrangBeskrivelse1", date, organizer, 20);
        Arrangement arrangement2 = new Arrangement("TestArrangID2", "TestArrangNavn2", "TestArrangBeskrivelse2", date, organizer, 20);
        Arrangement arrangement3 = new Arrangement("TestArrangID3", "TestArrangNavn3", "TestArrangBeskrivelse3", date, organizer, 20);
        arrayList.add(arrangement1);
        arrayList.add(arrangement2);
        arrayList.add(arrangement3);
        return arrayList;

    }

}

package hiof.gr19.seat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class OrganizerMode {
	private Scanner input;
	private Organizer organizer;

	public OrganizerMode(Scanner input) {
		this.input = input;
	}

	public void start(){

		//TODO: Create and select Organizer

		Console.inform("** Organizer mode **");
		Console.inform("1 = New organizer");
		Console.inform("2 = Returning organizer");
		Console.promptInput();

		int selectedOption = Integer.parseInt(input.nextLine());

		System.out.println(selectedOption);

		switch (selectedOption){
			case 1:
				createOrganizer();
				break;
			case 2:
				selectOrganizer();
				break;
			default:
				Console.selectionError(selectedOption);
				break;
		}

		selectMode();
	}


	private void selectMode(){

		Console.inform("1 = Create arrangement");
		Console.inform("2 = Manage arrangement");
		Console.inform("3 = Manage organizer info");
		Console.promptInput();

		int selectedOption = Integer.parseInt(input.nextLine());

		switch (selectedOption) {
			case 1:
				createArrangement();
				break;
			case 2:
				editArrangement();
				break;
			case 3:
				editOrganizer();
				break;
			default:
				Console.selectionError(selectedOption);
				break;
		}
	}


	private void createOrganizer() {
		Console.inform("Create organizer");
		//TODO: Make it changeable l8r
		Console.inform("Information entered can be changed later");

		Console.promptInput("Enter organizer name");
		String organizerName = input.nextLine();

		//TODO: Smart ID

		Organizer organizer = new Organizer("testID", organizerName);

		this.organizer = organizer;
		selectMode();
	}


	private void editOrganizer(){
		Console.inform("Editing: " + organizer);

		Console.promptInput("Enter new organizer name");

		String organizerName = input.nextLine();

		ModelEditor.changeOrganizer(organizer, organizerName);
	}


	private void createArrangement(){
		Console.inform("Create arrangement.");
		//TODO: Make it changeable...
		Console.inform("Information entered can be changed later");

		Console.promptInput("Enter arrangement title");
		String arrangementTitle = input.nextLine();

		Console.promptInput("Enter arrangement description");
		String arrangementDescription = input.nextLine();

		Console.promptInput("Enter arrangement date in format: yyyy-mm-dd");
		String arrangementDate = input.nextLine();

		Console.promptInput("Enter arrangement start time in format 24 clock: hh:mm");
		String arrangementTime =  input.nextLine();

		Date arrangementFinalDate = DateFormatter.stringDateAndTimeToDate(arrangementDate, arrangementTime);

		Organizer arrangementOrganizer = new Organizer("testOrgID","Test org inc");

		Console.promptInput("Enter attendee cap");
		int arrangementHumanCap = Integer.parseInt(input.nextLine());

		//TODO: Smart ID
		//TODO: Gjøre noe med "arrangement"
		Arrangement arrangement = new Arrangement("testID", arrangementTitle, arrangementDescription, arrangementFinalDate, arrangementOrganizer, arrangementHumanCap);

		//TODO: Merge? :)
		//database.createEvent(arrangement)  ??
		//database.createEvent(arrangement.getTitle, arrangement.getDescription, osv)  ??
	}


	private void selectOrganizer(){
		ArrayList<Organizer> organizers = placeholderOrganizerDB();

		Console.inform("Select organizer.");
		Console.selectFromList(organizers);

		int organizerIndex = Integer.parseInt(input.nextLine());

		try {
			Organizer organizer = organizers.get(organizerIndex);

			this.organizer = organizer;
		}catch (IndexOutOfBoundsException ex){
			Console.selectionError(organizerIndex);
		}
	}


	private void editArrangement(){
		ArrayList<Arrangement> arrangements = placeholderArrangementDB();

		Console.inform("Manage arrangement.");
		Console.newLine();

		Console.inform("Select arrangement.");
		Console.selectFromList(arrangements);

		int arrangementIndex = Integer.parseInt(input.nextLine());
		Arrangement selectedArrangement = arrangements.get(arrangementIndex);

		Console.inform("Select option");
		Console.newLine();

		Console.inform("1 = change title");
		Console.inform("2 = change description");
		Console.inform("3 = change date (yyyy-mm-dd/hh:mm)");
		Console.inform("4 = change organizer"); //Er dette logisk?
		Console.inform("5 = change attendee cap");
		Console.promptInput();

		int selectedChangeOption = Integer.parseInt(input.nextLine());

		Console.promptInput("Change to?");

		String changeValue = input.nextLine();

		ModelEditor.changeArrangement(selectedArrangement, selectedChangeOption, changeValue);

		Console.inform(selectedArrangement.getTotalInformation());
	}


	private ArrayList<Arrangement> placeholderArrangementDB (){ // SKAL BORT

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


	private ArrayList<Organizer> placeholderOrganizerDB () {

		ArrayList<Organizer> arrayList = new ArrayList<>();

		Organizer organizer1 = new Organizer("TestOrgID1", "TestOrgNavn1");
		Organizer organizer2 = new Organizer("TestOrgID2", "TestOrgNavn2");
		Organizer organizer3 = new Organizer("TestOrgID3", "TestOrgNavn3");
		arrayList.add(organizer1);
		arrayList.add(organizer2);
		arrayList.add(organizer3);

		return arrayList;
	}
}

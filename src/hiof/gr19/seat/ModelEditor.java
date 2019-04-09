package hiof.gr19.seat;

public class ModelEditor {

	public static void changeArrangement(Arrangement arrangement, int fieldToChange, String changeValue){
		switch (fieldToChange) {
			case 1:
				arrangement.setArrangmentTitle(changeValue);
				break;
			case 2:
				arrangement.setArrangmentDescription(changeValue);
				break;
			case 3:
				arrangement.setArragmentDate(DateFormatter.stringDateAndTimeToDate(changeValue));
				break;
			case 4:
				//TODO: Needs looks
				//arrangement.setOrganizer();
				break;
			case 5:
				int peopleAmount = Integer.parseInt(changeValue);
				arrangement.setMaxAttendees(peopleAmount);
				break;
			default:
				throw new IndexOutOfBoundsException("Index " + fieldToChange + " is out of bounds!");
		}
	}

	public static void changeOrganizer(Organizer organizer, String changeValue){
		organizer.setOrganizerName(changeValue);
	}
}

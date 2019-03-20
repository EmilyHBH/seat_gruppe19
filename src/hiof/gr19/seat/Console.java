package hiof.gr19.seat;

import java.util.ArrayList;

public class Console {

	public static void promptInput(String text) {
		System.out.println(text);
		System.out.print(">");
	}

	public static void promptInput(){
		System.out.println();
		System.out.print(">");
	}

	public static void inform(String text) {
		System.out.println(text);
	}

	public static void newLine(){
		System.out.println();
	}

	public static void selectFromList(ArrayList arrayList){
		for (int i = 0; i < arrayList.size(); i++){
			Console.inform(i + " = " + arrayList.get(i));
		}
		promptInput();

	}

	public static void selectionError(int index){
		IndexOutOfBoundsException ioobe = new IndexOutOfBoundsException(index);
		ioobe.printStackTrace();
		newLine();
		inform("Please follow instructions.");
		inform("Restarting application");
		newLine();
		Seat.main(null);
	}
}

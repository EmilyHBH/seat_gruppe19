package hiof.gr19.seat.console.ui;

import java.util.ArrayList;
import java.util.Scanner;

class InputValidator {

    static Scanner scanner = new Scanner(System.in);

    static boolean askBooleanQuestionAndReturnAnswer(String question){
        scanner = new Scanner(System.in);
        System.out.println(questionFormat(question + " (y/n)"));

        String answer;
        while(true){
            answer = scanner.nextLine();

            if(answer.equals("y"))
                return true;
            else if(answer.equals("n"))
                return false;

            System.out.println("Answer by typing 'y' or 'n'");
        }
    }
    static int validateIntInput(String question){
        scanner = new Scanner(System.in);
        System.out.println(questionFormat(question));

        int result;

        while(true){
            try{
                result = Integer.parseInt(scanner.nextLine());
                return result;
            }
            catch(NumberFormatException e){
                System.out.println("Only numeric whole numbers are valid. Try again:");
            }
        }
    }
    static String validateStringInput(String question){
        scanner = new Scanner(System.in);
        System.out.println(questionFormat(question));

        while(true){
            try{
                return scanner.nextLine();
            }
            catch(Exception e){
                System.out.println("Something went wrong, try again:");
            }
        }
    }
    static int selectFromList(ArrayList arrayList){
        for (int i = 1; i < arrayList.size() +1; i++)
            System.out.println(i + " = " + arrayList.get(i-1));

        int choice = validateIntInput("Make a choice based on the id/nr");

        while(choice < 1 && choice >= arrayList.size() +1){
            System.out.println("The number you gave is not in range, choose again");
            choice = validateIntInput("Make a choice based on the id/nr");
        }

        return choice;
    }
    private static String questionFormat(String question){
        return question == null ? null : "\n" + question + ":\n";
    }

}

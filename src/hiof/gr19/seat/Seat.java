package hiof.gr19.seat;

import java.io.Console;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Seat {
    public static void main(String[] args){
        identifyUser();
    }

    private static void identifyUser() {
        Console console = System.console();
        if (console == null){
            throw new NullPointerException("No console found");
        }


        System.out.println("OP mode?");
        System.out.println("1 = Organizer");
        System.out.println("2 = Not Organizer");
        String input = console.readLine(">");




    }
}

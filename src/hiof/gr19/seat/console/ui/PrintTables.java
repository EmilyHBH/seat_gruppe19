package hiof.gr19.seat.console.ui;

import de.vandermeer.asciitable.AsciiTable;
import hiof.gr19.seat.model.Arrangement;
import hiof.gr19.seat.model.Ticket;

import java.util.ArrayList;

class PrintTables {

    static void printArrangements(ArrayList<Arrangement> arrangementList){

        if (arrangementList.size() == 0){
            System.out.println("No events");
            return;
        }

        AsciiTable arragmentTable = new AsciiTable();

        arragmentTable.addRule();
        arragmentTable.addRow("ID","Arrangment","Date","Ticketavaliable");

        for (int i = 0; i < arrangementList.size(); i++) {
            arragmentTable.addRule();
            int ledigeBiletter = 0;
            for(Ticket ticket : arrangementList.get(i).getAvailableTickets())
                ledigeBiletter += ticket.getAntall();

            arragmentTable.addRow(i,arrangementList.get(i).getArrangmentTitle(),arrangementList.get(i).getArragmentDate(),ledigeBiletter);
            arragmentTable.addRule();

        }

        String table = arragmentTable.render();
        System.out.println(table);

    }
    static void printTickets(ArrayList<Ticket> tickets){
        if (tickets.size() == 0){
            System.out.println("No tickets");
            return;
        }

        AsciiTable ticketTable = new AsciiTable();

        ticketTable.addRule();
        ticketTable.addRow("ID", "Description", "Tickets avaliable", "Price");

        for(int i = 0; i < tickets.size(); i++){
            ticketTable.addRule();
            ticketTable.addRow(i, tickets.get(i).getBeskrivelse(), tickets.get(i).getAntall(), tickets.get(i).getPris());
            ticketTable.addRule();
        }

        String table = ticketTable.render();
        System.out.println(table);
    }

}

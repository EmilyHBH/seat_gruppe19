package hiof.gr19.seat.stubs.confirmation;

import de.vandermeer.asciitable.AsciiTable;
import hiof.gr19.seat.model.Purchase;

public class PrintReciept extends ConfirmationMethod {
    @Override
    public void sendConfirmation(Purchase purchase) {
        AsciiTable receiptTable = new AsciiTable();

        receiptTable.addRule();
        receiptTable.addRow("Owner Name", "TicketID", "Valid for (#people)");
        receiptTable.addRow(purchase.getOwnerName(), purchase.getTicket().getId(), purchase.getTicketAmount());
        receiptTable.addRule();

        String table = receiptTable.render();
        System.out.println(table);
    }
}

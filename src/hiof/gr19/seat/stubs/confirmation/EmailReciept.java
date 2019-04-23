package hiof.gr19.seat.stubs.confirmation;

import hiof.gr19.seat.model.Purchase;

public class EmailReciept extends ConfirmationMethod {

    private String email;

    public EmailReciept(String email) {
        this.email = email;
    }

    @Override
    public void sendConfirmation(Purchase purchase) {
        /*
        MailMessage message = new MailMessage();
        message.setFrom();
        message.addRecipient(email);
        message.setSubject("Ticket purchase confirmation");
        message.setText("successfully purchased ticket");

        Transport.send(message);
         */

        System.out.println("Purchase confirmation sent to: " + email);
    }
}

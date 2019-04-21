package hiof.gr19.seat.stubs.confirmation;

import hiof.gr19.seat.stubs.confirmation.ConfirmationMethod;

public class emailReciept extends ConfirmationMethod {

    private String email;

    @Override
    public void sendConfirmation() {
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

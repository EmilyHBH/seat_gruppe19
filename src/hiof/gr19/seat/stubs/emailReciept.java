package hiof.gr19.seat.stubs;

public class emailReciept extends ConfirmationMethod {

    private String email;

    public emailReciept(String email) {
        this.email = email;
    }

    @Override
    public void sendConfirmation() {

    }
}

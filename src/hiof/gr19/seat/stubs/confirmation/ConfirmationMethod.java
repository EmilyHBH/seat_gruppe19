package hiof.gr19.seat.stubs.confirmation;

import hiof.gr19.seat.model.Purchase;

public abstract class ConfirmationMethod {
    public abstract void sendConfirmation(Purchase purchase);
}

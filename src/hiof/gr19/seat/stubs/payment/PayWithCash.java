package hiof.gr19.seat.stubs.payment;

public class PayWithCash extends PaymentMethod {
	@Override
	public boolean pay(String navn) {
		System.out.println("Paying with cash ...");
		BetalingsStub transaksjonsService = new BetalingsStub(navn);
		return transaksjonsService.godkjentBetaling();
	}
}

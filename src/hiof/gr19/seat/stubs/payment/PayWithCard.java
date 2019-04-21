package hiof.gr19.seat.stubs.payment;

public class PayWithCard extends PaymentMethod {
	@Override
	public boolean pay(String navn) {
		BetalingsStub transaksjonsService = new BetalingsStub(navn);
		return transaksjonsService.godkjentBetaling();
	}
}

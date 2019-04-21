package hiof.gr19.seat.stubs.payment;

public class PayWithVipps extends PaymentMethod {

	int telefonnummer;

	public PayWithVipps(int telefonnummer) {
		this.telefonnummer = telefonnummer;
	}

	@Override
	public boolean pay(String navn) {
		BetalingsStub transaksjonsService = new BetalingsStub(telefonnummer, navn);
		return transaksjonsService.godkjentBetaling();
	}
}

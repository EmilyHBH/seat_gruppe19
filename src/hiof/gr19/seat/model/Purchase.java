package hiof.gr19.seat.model;

import hiof.gr19.seat.model.Arrangement;
import hiof.gr19.seat.model.Ticket;
import hiof.gr19.seat.stubs.confirmation.ConfirmationMethod;
import hiof.gr19.seat.stubs.payment.PaymentMethod;

public class Purchase {
	private Arrangement arrangement;
	//evt Ticket ticket isteden for int ticketID
	//Tror egentlig alle variablene i denne klassen burde ha vært egene klasser.
	private String ownerName;
	private Ticket ticket; //Kan brukes istedenfor ticketID med database implementasjon getTicketByID();
	private int ticketAmount;
	private ConfirmationMethod confirmationMethod;
	private PaymentMethod paymentMethod;
	private boolean paymentStatus;

	public Purchase(Arrangement arrangement) {
		this.arrangement = arrangement;
	}

	public void printReceipt(){
		confirmationMethod.sendConfirmation(this);
	}

	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public int getTicketAmount() {
		return ticketAmount;
	}
	public void setTicketAmount(int ticketAmount) {
		this.ticketAmount = ticketAmount;
	}
	public ConfirmationMethod getConfirmationMethod() {
		return confirmationMethod;
	}
	public void setConfirmationMethod(ConfirmationMethod confirmationMethod) {
		this.confirmationMethod = confirmationMethod;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public boolean isPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Arrangement getArrangement() {
		return arrangement;
	}
	public void setArrangement(Arrangement arrangement) {
		this.arrangement = arrangement;
	}
}

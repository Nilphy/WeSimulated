package edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole;

import java.util.Date;
import java.util.List;

import javafx.util.Pair;
import edu.wesimulated.firstapp.simulation.domain.PersonCharacteristic;
import edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole.Message.Status;
import edu.wesimulated.firstapp.view.ThingsWithoutAUi;

public class EmailClient extends ComunicativeEntity implements HighlyInterruptibleRolePrioritized {

	public EmailClient(HighlyInterruptibleRolePerson person) {
		super(person);
	}

	@Override
	protected void finishProcessingOfPreviousMessage() {
		switch (this.getMessageInProcess().getStatus()) {
		case NEW:
			this.getMessageInProcess().analize();
		case RESPOND:
			this.getMessageInProcess().setStatus(Status.PROCESSED);
		default:
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Float getMaxPriority() {
		return this.getPerson().getProfile().get(PersonCharacteristic.MaxPriorityOfEmail).getNumber().floatValue();
	}

	@Override
	public MessageValuator createMessageAgeValuator() {
		return new MessageValuator(ThingsWithoutAUi.MIN_ANTIQUITY_EMAIL_IN_MINUTES, ThingsWithoutAUi.MED_ANTIQUITY_EMAIL_IN_MINUTES);
	}

	@Override
	protected MessageValuator createMessageAmountValuator() {
		return new MessageValuator(ThingsWithoutAUi.MIN_AMOUNT_EMAIL, ThingsWithoutAUi.MED_AMOUNT_EMAIL);
	}

	@Override
	protected Pair<Date, Message> resolvePendingMessages() {
		return this.getPerson().resolvePendingEmails();
	}

	@Override
	protected List<Message> getPendingMessagesFromPerson() {
		return this.getPerson().getPendingEmails();
	}
}

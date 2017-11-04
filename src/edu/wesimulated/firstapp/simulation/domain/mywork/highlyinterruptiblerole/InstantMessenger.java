package edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole;

import java.util.Date;
import java.util.List;

import javafx.util.Pair;
import edu.wesimulated.firstapp.simulation.domain.PersonCharacteristic;
import edu.wesimulated.firstapp.view.ThingsWithoutAUi;

/**
 * 
 * This task is done by the highlyInterruptibleRole periodically
 * 
 * @author Carolina
 *
 */
public class InstantMessenger extends ComunicativeEntity {

	public InstantMessenger(Person person) {
		super(person);
	}

	@Override
	protected void finishProcessingOfPreviousMessage() {
		this.getMessageInProcess().setStatus(Message.Status.PROCESSED);
	}

	@Override
	public Float getMaxPriority() {
		return this.getPerson().getProfile().get(PersonCharacteristic.MAX_PRIORITY_IM).getNumber().floatValue();
	}

	@Override
	public MessageValuator createMessageAgeValuator() {
		return new MessageValuator(ThingsWithoutAUi.MIN_ANTIQUITY_IMMESSAGE_IN_MINUTES, ThingsWithoutAUi.MED_ANTIQUITY_IMMESSAGE_IN_MINUTES);
	}

	@Override
	protected MessageValuator createMessageAmountValuator() {
		return new MessageValuator(ThingsWithoutAUi.MIN_AMOUNT_IMMESAAGE, ThingsWithoutAUi.MED_AMOUNT_IMMESSAGE);
	}

	@Override
	protected Pair<Date, Message> resolvePendingMessages() {
		return this.getPerson().resolvePendingImMessages();
	}

	@Override
	protected List<Message> getPendingMessagesFromPerson() {
		return this.getPerson().getPendingImMessages();
	}
}

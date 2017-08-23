package edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javafx.util.Pair;
import edu.wesimulated.firstapp.simulation.domain.PersonCharacteristic;
import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.Message.Status;
import edu.wesimulated.firstapp.view.ThingsWithoutAUi;

public class Squealer extends ComunicativeEntity {

	public Squealer(HighlyInterruptibleRolePerson person) {
		super(person);
	}

	@Override
	public Float getMaxPriority() {
		return this.getPerson().getProfile().get(PersonCharacteristic.MaxPriorityOfFaceToFaceConversation).getNumber().floatValue();
	}

	@Override
	protected void finishProcessingOfPreviousMessage() {
		switch (this.getMessageInProcess().getStatus()) {
		case RESOLVE:
			this.getMessageInProcess().setStatus(Status.PROCESSED);
		case NOT_ISSUED:
			this.getMessageInProcess().setStatus(Status.ISSUED);
		default:
			throw new UnsupportedOperationException();
		}
	}

	@Override
	protected MessageValuator createMessageAgeValuator() {
		return new MessageValuator(ThingsWithoutAUi.MIN_ANTIQUITY_SQUEALER_IN_MINUTES, ThingsWithoutAUi.MED_ANTIQUITY_SQUEALER_IN_MINUTES);
	}

	@Override
	protected MessageValuator createMessageAmountValuator() {
		return new MessageValuator(ThingsWithoutAUi.MIN_AMOUNT_SQUEALER, ThingsWithoutAUi.MED_AMOUNT_SQUEALER);
	}

	@Override
	protected Pair<Date, Message> resolvePendingMessages() {
		return this.getPerson().resolvePendingSquealerNotifications();
	}

	@Override
	protected List<Message> getPendingMessagesFromPerson() {
		return this.getPerson().getPendingSquealerNotifications().stream().map((SquealerReport item) -> { return (Message) item; }).collect(Collectors.toList());
	}

}

package edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole;

import java.util.Date;
import java.util.List;

import javafx.util.Pair;
import edu.wesimulated.firstapp.simulation.domain.PersonCharacteristic;
import edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole.Message.Status;
import edu.wesimulated.firstapp.view.ThingsWithoutAUi;

public class Relationships extends ComunicativeEntity {

	private Person person;
	
	public Relationships(Person person) {
		super(person);
	}

	@Override
	public Float getMaxPriority() {
		return this.getPerson().getProfile().get(PersonCharacteristic.MAX_PRIORITY_FACE_TO_FACE_QUESTION).getNumber().floatValue();
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
	protected MessageValuator createMessageAgeValuator() {
		return new MessageValuator(ThingsWithoutAUi.MIN_ANTIQUITY_FACE_TO_FACE_QUESTION_IN_HOURS, ThingsWithoutAUi.MED_ANTIQUITY_FACE_TO_FACE_QUESTION_IN_HOURS);
	}

	@Override
	protected MessageValuator createMessageAmountValuator() {
		return new MessageValuator(ThingsWithoutAUi.MID_AMOUNT_FACE_TO_FACE_QUESTION, ThingsWithoutAUi.MED_AMOUNT_FACE_TO_FACE_QUESTION);
	}

	@Override
	protected Pair<Date, Message> resolvePendingMessages() {
		return this.person.resolvePendingFaceToFaceInteractions();
	}

	@Override
	protected List<Message> getPendingMessagesFromPerson() {
		return this.getPerson().getPendingFaceToFaceInteractions();
	}

}

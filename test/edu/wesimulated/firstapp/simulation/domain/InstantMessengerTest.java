package edu.wesimulated.firstapp.simulation.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.wesimulated.simulation.Clock;
import com.wesimulated.simulationmotor.DateUtils;

import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.HighlyInterruptibleRolePerson;
import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.ImMessage;
import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.InstantMessenger;
import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.MessageValuator.ValueLevel;

public class InstantMessengerTest {

	@Test
	public void testPriorityLists() {
		// FIXME create ImMessageBuilder
		Date date = new Date();
		Clock clock = new Clock(date, null, null);
		HighlyInterruptibleRolePerson sender = new HighlyInterruptibleRolePerson();
		HighlyInterruptibleRolePerson recipient = new HighlyInterruptibleRolePerson();
		InstantMessenger instantMessenger = new InstantMessenger(recipient);
		Date before = DateUtils.addMilis(date, -instantMessenger.getMessageAgeValuatorInstance().getMinutes(ValueLevel.LOW));
		Collection<HighlyInterruptibleRolePerson> recipients = new ArrayList<>();
		recipients.add(recipient);
		ImMessage message = new ImMessage(sender, recipients, before, clock);
		instantMessenger.getPerson().addUnreadImMessage(message);
		HighlyInterruptibleRolePerson otherRecipient = new HighlyInterruptibleRolePerson();
		recipients.add(otherRecipient);
		ImMessage otherMessage = new ImMessage(sender, recipients, before, clock);
		instantMessenger.getPerson().addUnreadImMessage(otherMessage);
		Assert.assertEquals(message, instantMessenger.getMostPrioritaryMessage());
	}
}

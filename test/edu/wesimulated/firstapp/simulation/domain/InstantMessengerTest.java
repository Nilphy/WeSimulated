package edu.wesimulated.firstapp.simulation.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.wesimulated.simulation.Clock;
import com.wesimulated.simulationmotor.DateUtils;

import edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole.Person;
import edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole.InstantMessenger;
import edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole.Message;
import edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole.MessageValuator.ValueLevel;

public class InstantMessengerTest {

	@Test
	public void testPriorityLists() {
		// FIXME create ImMessageBuilder
		Date date = new Date();
		Clock clock = new Clock(date, null, null);
		Person sender = new Person();
		Person recipient = new Person();
		InstantMessenger instantMessenger = new InstantMessenger(recipient);
		Date before = DateUtils.addMilis(date, -instantMessenger.getMessageAgeValuatorInstance().getMinutes(ValueLevel.LOW));
		Collection<Person> recipients = new ArrayList<>();
		recipients.add(recipient);
		Message message = new Message(sender, recipients, before, clock);
		instantMessenger.getPerson().addUnreadImMessage(message);
		Person otherRecipient = new Person();
		recipients.add(otherRecipient);
		Message otherMessage = new Message(sender, recipients, before, clock);
		instantMessenger.getPerson().addUnreadImMessage(otherMessage);
		Assert.assertEquals(message, instantMessenger.getMostPrioritaryMessage());
	}
}

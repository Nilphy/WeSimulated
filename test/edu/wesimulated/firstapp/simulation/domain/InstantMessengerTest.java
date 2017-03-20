package edu.wesimulated.firstapp.simulation.domain;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.wesimulated.simulation.Clock;
import com.wesimulated.simulationmotor.DateUtils;

import edu.wesimulated.firstapp.simulation.domain.ImMessage.Antiquity;

public class InstantMessengerTest {

	@Test
	public void testPriorityLists() {
		Date date = new Date();
		Clock clock = new Clock(date, null, null);
		InstantMessenger task = new InstantMessenger();
		Person sender = new Person();
		Person recipient = new Person();
		Date before = DateUtils.addMilis(date, -(Antiquity.LOW.get() * 1000));
		ImMessage message = new ImMessage(sender, before, clock);
		message.addRecipient(recipient);
		task.addUnreadMessage(message);
		Person otherRecipient = new Person();
		ImMessage otherMessage = new ImMessage(sender, before, clock);
		otherMessage.addRecipient(recipient);
		otherMessage.addRecipient(otherRecipient);
		task.addUnreadMessage(otherMessage);
		Assert.assertEquals(message, task.getMostPrioritaryMessage());
	}
}

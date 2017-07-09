package edu.wesimulated.firstapp.simulation.domain;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.wesimulated.simulation.Clock;
import com.wesimulated.simulationmotor.DateUtils;

import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.HighlyInterruptibleRolePerson;
import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.ImMessage;
import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.InstantMessenger;
import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.ImMessage.Antiquity;

public class InstantMessengerTest {

	@Test
	public void testPriorityLists() {
		Date date = new Date();
		Clock clock = new Clock(date, null, null);
		HighlyInterruptibleRolePerson sender = new HighlyInterruptibleRolePerson();
		HighlyInterruptibleRolePerson recipient = new HighlyInterruptibleRolePerson();
		InstantMessenger task = new InstantMessenger(recipient);
		Date before = DateUtils.addMilis(date, -(Antiquity.LOW.get() * 1000));
		ImMessage message = new ImMessage(sender, before, clock);
		message.addRecipient(recipient);
		task.addUnreadMessage(message);
		HighlyInterruptibleRolePerson otherRecipient = new HighlyInterruptibleRolePerson();
		ImMessage otherMessage = new ImMessage(sender, before, clock);
		otherMessage.addRecipient(recipient);
		otherMessage.addRecipient(otherRecipient);
		task.addUnreadMessage(otherMessage);
		Assert.assertEquals(message, task.getMostPrioritaryMessage());
	}
}

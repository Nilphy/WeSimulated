import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import edu.wesimulated.firstapp.persistence.XmlSerializationTest;
import edu.wesimulated.firstapp.simulation.domain.CheckIMTest;
import edu.wesimulated.firstapp.simulation.stochastic.TestFixedClassProbability;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestFixedClassProbability.class, XmlSerializationTest.class, CheckIMTest.class })
public class TestSuitFirst {
}

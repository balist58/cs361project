package cs361Project;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;



@RunWith(Suite.class)
@Suite.SuiteClasses({
	ControlTest.class,
	InitializationTest.class,
	IndividualRunTest.class,
	ParallelIndividualRunTest.class,
	GroupRunTest.class
})
public class CtTestSuite {
}

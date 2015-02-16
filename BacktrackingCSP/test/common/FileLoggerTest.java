package common;

import java.util.Arrays;

import org.junit.Test;

public class FileLoggerTest {

	
	
	
	@Test
	public void test_deactivated() {
		String[] args = {"--no-logs"};
		
		FileLogger fl = new FileLogger("Test_log.txt", Arrays.asList(args));

		fl.logException(new Exception("HAHA its deactivated"));
		fl.print("no Pooop");

		fl.activate();
		fl.println("new line!");
		fl.print("Pooop");
		fl.logException(new Exception("Oh jeeze..."));

		fl.close();
	}
	
	
	
}

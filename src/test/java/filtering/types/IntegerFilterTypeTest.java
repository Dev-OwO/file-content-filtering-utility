package filtering.types;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import filtering.types.IntegerFilterType.IntegerFilterStatistic;

public class IntegerFilterTypeTest {
	private String LOG_SHORT;
	private String LOG_FULL;
	private IntegerFilterType ift;
	private IntegerFilterStatistic ifs;
	
	@BeforeEach
	public void createFilter() {
		ift = new IntegerFilterType();
		ifs = (IntegerFilterStatistic)ift.getFilterStatistic();
		LOG_SHORT = FilterStatistic.LOG_SHORT.replaceFirst("%s", "integers");
		LOG_FULL = FilterNumberStatistic.LOG_FULL.replaceFirst("%s", "integers");
	}
	
	@Test
	public void empty() {
		Assertions.assertEquals(Collections.emptyList(), ift.getAll());
		String logShort = String.format(LOG_SHORT, 0);
		Assertions.assertEquals(logShort, ifs.getShort());
		String logFull = String.format(LOG_FULL, 0, "-", "-", "-", "-");
		Assertions.assertEquals(logFull, ifs.getFull());
	}
	
	@Test
	public void wrongValues() {
		Assertions.assertFalse(ift.isItType(null));
		Assertions.assertFalse(ift.isItType(""));
		Assertions.assertFalse(ift.isItType("null"));
		Assertions.assertFalse(ift.isItType("Строка"));
		Assertions.assertFalse(ift.isItType("45.67f"));
		Assertions.assertFalse(ift.isItType("453.06575"));
		Assertions.assertFalse(ift.isItType("true"));
		Assertions.assertFalse(ift.isItType(String.valueOf(Float.MAX_VALUE)));
		Assertions.assertFalse(ift.isItType(String.valueOf(Float.MIN_VALUE)));
	}
	
	@Test
	public void rightValues() {
		Assertions.assertTrue(ift.isItType("0"));
		Assertions.assertTrue(ift.isItType("256"));
		Assertions.assertTrue(ift.isItType("-256"));
		Assertions.assertTrue(ift.isItType(String.valueOf(Integer.MAX_VALUE)));
		Assertions.assertTrue(ift.isItType(String.valueOf(Integer.MIN_VALUE)));
		
		Assertions.assertTrue(ift.isItType("45"));
		Assertions.assertTrue(ift.isItType("100500"));
		Assertions.assertTrue(ift.isItType("1234567890123456789"));
	}
	
	@Test
	public void one() {
		ift.add("256");
		String logShort = String.format(LOG_SHORT, 1);
		Assertions.assertEquals(logShort, ifs.getShort());
		String logFull = String.format(LOG_FULL, 1, "256", "256", "256", "256");
		Assertions.assertEquals(logFull, ifs.getFull());
	}
	
	@Test
	public void two() {
		ift.add("-128");
		ift.add("256");
		String logShort = String.format(LOG_SHORT, 2);
		Assertions.assertEquals(logShort, ifs.getShort());
		String logFull = String.format(LOG_FULL, 2, "-128", "256", "128", "64");
		Assertions.assertEquals(logFull, ifs.getFull());
	}
	
	@Test
	public void three() {
		String maxInt = String.valueOf(Integer.MAX_VALUE);
		ift.add(maxInt);
		ift.add(maxInt);
		ift.add("333");
		String logShort = String.format(LOG_SHORT, 3);
		Assertions.assertEquals(logShort, ifs.getShort());
		String logFull = String.format(LOG_FULL, 3, "333", maxInt, "4294967627", "1431655875.66667");
		Assertions.assertEquals(logFull, ifs.getFull());
	}
}

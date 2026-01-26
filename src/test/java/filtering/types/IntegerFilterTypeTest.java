package filtering.types;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import filtering.types.IntegerFilterType.IntegerFilterStatistic;

public class IntegerFilterTypeTest {
	private String LOG_SHORT;
	private String LOG_FULL;
	private IntegerFilterType ift;
	private IntegerFilterStatistic ifs;
	
	@Before
	public void createFilter() {
		ift = new IntegerFilterType();
		ifs = (IntegerFilterStatistic)ift.getFilterStatistic();
		LOG_SHORT = FilterStatistic.LOG_SHORT;
		LOG_FULL = FilterNumberStatistic.LOG_FULL;
	}
	
	@Test
	public void empty() {
		Assert.assertEquals(Collections.emptyList(), ift.getAll());
		String logShort = String.format(LOG_SHORT, 0);
		Assert.assertEquals(logShort, ifs.getShort());
		String logFull = String.format(LOG_FULL, 0, "-", "-", "-", "-");
		Assert.assertEquals(logFull, ifs.getFull());
	}
	
	@Test
	public void wrongValues() {
		Assert.assertFalse(ift.isItType(null));
		Assert.assertFalse(ift.isItType(""));
		Assert.assertFalse(ift.isItType("Строка"));
		Assert.assertFalse(ift.isItType("45.67f"));
		Assert.assertFalse(ift.isItType("453.06575"));
		Assert.assertFalse(ift.isItType("true"));
		Assert.assertFalse(ift.isItType(String.valueOf(Float.MAX_VALUE)));
		Assert.assertFalse(ift.isItType(String.valueOf(Float.MIN_VALUE)));
	}
	
	@Test
	public void rightValues() {
		Assert.assertTrue(ift.isItType("0"));
		Assert.assertTrue(ift.isItType("256"));
		Assert.assertTrue(ift.isItType("-256"));
		Assert.assertTrue(ift.isItType(String.valueOf(Integer.MAX_VALUE)));
		Assert.assertTrue(ift.isItType(String.valueOf(Integer.MIN_VALUE)));
		
		Assert.assertTrue(ift.isItType("45"));
		Assert.assertTrue(ift.isItType("100500"));
		Assert.assertTrue(ift.isItType("1234567890123456789"));
	}
	
	@Test
	public void one() {
		ift.add("256");
		String logShort = String.format(LOG_SHORT, 1);
		Assert.assertEquals(logShort, ifs.getShort());
		String logFull = String.format(LOG_FULL, 1, "256", "256", "256", "256");
		Assert.assertEquals(logFull, ifs.getFull());
	}
	
	@Test
	public void two() {
		ift.add("-128");
		ift.add("256");
		String logShort = String.format(LOG_SHORT, 2);
		Assert.assertEquals(logShort, ifs.getShort());
		String logFull = String.format(LOG_FULL, 2, "-128", "256", "128", "64");
		Assert.assertEquals(logFull, ifs.getFull());
	}
	
	@Test
	public void three() {
		String maxInt = String.valueOf(Integer.MAX_VALUE);
		ift.add(maxInt);
		ift.add(maxInt);
		ift.add("333");
		String logShort = String.format(LOG_SHORT, 3);
		Assert.assertEquals(logShort, ifs.getShort());
		String logFull = String.format(LOG_FULL, 3, "333", maxInt, "4294967627", "1.4316558756666667E9");
		Assert.assertEquals(logFull, ifs.getFull());
	}
	
	// TODO не получилось
//	@Test
//	public void spetialValues() {
//		ift.add("-4_030");
//		ift.add("650_4444");
//		String logShort = String.format(LOG_SHORT, 2);
//		Assert.assertEquals(logShort, ifs.getShort());
//		String logFull = String.format(LOG_FULL, 2, "-4_030", "650_4444", "6500414", "3250207");
//		Assert.assertEquals(logFull, ifs.getFull());
//	}
}

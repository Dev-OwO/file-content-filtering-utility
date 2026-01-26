package filtering.types;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import filtering.types.FloatFilterType.FloatFilterStatistic;

public class FloatFilterTypeTest {
	private String LOG_SHORT;
	private String LOG_FULL;
	private FloatFilterType fft;
	private FloatFilterStatistic ffs;
	
	@Before
	public void createFilter() {
		fft = new FloatFilterType();
		ffs = (FloatFilterStatistic)fft.getFilterStatistic();
		LOG_SHORT = FilterStatistic.LOG_SHORT;
		LOG_FULL = FilterNumberStatistic.LOG_FULL;
	}
	
	@Test
	public void empty() {
		Assert.assertEquals(Collections.emptyList(), fft.getAll());
		String logShort = String.format(LOG_SHORT, 0);
		Assert.assertEquals(logShort, ffs.getShort());
		String logFull = String.format(LOG_FULL, 0, "-", "-", "-", "-");
		Assert.assertEquals(logFull, ffs.getFull());
	}
	
	@Test
	public void wrongValues() {
		Assert.assertFalse(fft.isItType(null));
		Assert.assertFalse(fft.isItType(""));
		Assert.assertFalse(fft.isItType("Строка"));
		Assert.assertFalse(fft.isItType("100-455"));
		Assert.assertFalse(fft.isItType("true"));
		Assert.assertFalse(fft.isItType("Infinity"));
		Assert.assertFalse(fft.isItType("-Infinity"));
		Assert.assertFalse(fft.isItType("NaN"));
		Assert.assertFalse(fft.isItType(String.valueOf(Double.MAX_VALUE)));
		Assert.assertFalse(fft.isItType(String.valueOf(Double.MIN_VALUE)));
		Assert.assertFalse(fft.isItType(String.valueOf("3.5E38")));
		Assert.assertFalse(fft.isItType(String.valueOf("350000000000000000000000000000000000000"))); // 10 в 38 степени
	}
	
	@Test
	public void rightValues() {
		Assert.assertTrue(fft.isItType("0"));
		Assert.assertTrue(fft.isItType("256"));
		Assert.assertTrue(fft.isItType("-256"));
		Assert.assertTrue(fft.isItType("100.245"));
		Assert.assertTrue(fft.isItType(String.valueOf(Float.MAX_VALUE)));
		Assert.assertTrue(fft.isItType(String.valueOf(Float.MIN_VALUE)));
		Assert.assertTrue(fft.isItType(String.valueOf("340000000000000000000000000000000000000"))); // 10 в 38 степени
		
		Assert.assertTrue(fft.isItType("3.1415"));
		Assert.assertTrue(fft.isItType("-0.001"));
		Assert.assertTrue(fft.isItType("1.528535047E-25"));
	}
	
//	@Test
//	public void one() {
//		fft.add("76.56");
//		String logShort = String.format(LOG_SHORT, 1);
//		Assert.assertEquals(logShort, ffs.getShort());
//		String logFull = String.format(LOG_FULL, 1, "76.56", "76.56", "76.56", "76.56");
//		Assert.assertEquals(logFull, ffs.getFull());
//	}
//	
//	@Test
//	public void two() {
//		fft.add("3000000000000");
//		fft.add("6000000000000");
//		String logShort = String.format(LOG_SHORT, 2);
//		Assert.assertEquals(logShort, ffs.getShort());
//		String logFull = String.format(LOG_FULL, 2, "3000000000000", "6000000000000",
//				"9000000000000", "4500000000000");
//		Assert.assertEquals(logFull, ffs.getFull());
//	}
}



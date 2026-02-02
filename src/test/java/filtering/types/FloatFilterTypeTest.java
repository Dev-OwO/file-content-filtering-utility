package filtering.types;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import filtering.types.FloatFilterType.FloatFilterStatistic;

public class FloatFilterTypeTest {
	private String LOG_SHORT;
	private String LOG_FULL;
	private FloatFilterType fft;
	private FloatFilterStatistic ffs;
	
	@BeforeEach
	public void createFilter() {
		fft = new FloatFilterType();
		ffs = (FloatFilterStatistic)fft.getFilterStatistic();
		LOG_SHORT = FilterStatistic.LOG_SHORT.replaceFirst("%s", "floats");
		LOG_FULL = FilterNumberStatistic.LOG_FULL.replaceFirst("%s", "floats");
	}
	
	@Test
	public void empty() {
		Assertions.assertEquals(Collections.emptyList(), fft.getAll());
		String logShort = String.format(LOG_SHORT, 0);
		Assertions.assertEquals(logShort, ffs.getShort());
		String logFull = String.format(LOG_FULL, 0, "-", "-", "-", "-");
		Assertions.assertEquals(logFull, ffs.getFull());
	}
	
	@Test
	public void wrongValues() {
		Assertions.assertFalse(fft.isItType(null));
		Assertions.assertFalse(fft.isItType(""));
		Assertions.assertFalse(fft.isItType("null"));
		Assertions.assertFalse(fft.isItType("Строка"));
		Assertions.assertFalse(fft.isItType("100-455"));
		Assertions.assertFalse(fft.isItType("true"));
		Assertions.assertFalse(fft.isItType("Infinity"));
		Assertions.assertFalse(fft.isItType("-Infinity"));
		Assertions.assertFalse(fft.isItType("NaN"));
	}
	
	@Test
	public void rightValues() {
		Assertions.assertTrue(fft.isItType("0"));
		Assertions.assertTrue(fft.isItType("256"));
		Assertions.assertTrue(fft.isItType("-256"));
		Assertions.assertTrue(fft.isItType("100.245"));
		Assertions.assertTrue(fft.isItType(String.valueOf(Double.MAX_VALUE)));
		Assertions.assertTrue(fft.isItType(String.valueOf(Double.MIN_VALUE)));
		
		Assertions.assertTrue(fft.isItType("3.1415"));
		Assertions.assertTrue(fft.isItType("-0.001"));
		Assertions.assertTrue(fft.isItType("1.528535047E-25"));
	}
}



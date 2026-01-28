package filtering.types;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import filtering.types.StringFilterType.StringFilterStatistic;

public class StringFilterTypeTest {
	private String LOG_SHORT;
	private String LOG_FULL;
	private StringFilterType sft;
	private StringFilterStatistic sfs;
	
	@Before
	public void createFilter() {
		sft = new StringFilterType();
		sfs = (StringFilterStatistic)sft.getFilterStatistic();
		LOG_SHORT = FilterStatistic.LOG_SHORT;
		LOG_FULL = FilterStatistic.LOG_FULL;
	}
	
	@Test
	public void empty() {
		Assert.assertEquals(Collections.emptyList(), sft.getAll());
		String logShort = String.format(LOG_SHORT, 0);
		Assert.assertEquals(logShort, sfs.getShort());
		String logFull = String.format(LOG_FULL, 0, "-", "-");
		Assert.assertEquals(logFull, sfs.getFull());
	}
	
	@Test
	public void wrongValues() {
		Assert.assertFalse(sft.isItType(null));
		Assert.assertFalse(sft.isItType(""));
	}
	
	@Test
	public void rightValues() {
		Assert.assertTrue(sft.isItType("0"));
		Assert.assertTrue(sft.isItType("    "));
		
		Assert.assertTrue(sft.isItType("Lorem ipsum dolor sit amet"));
		Assert.assertTrue(sft.isItType("Пример"));
		Assert.assertTrue(sft.isItType("consectetur adipiscing"));
		Assert.assertTrue(sft.isItType("тестовое задание"));
		Assert.assertTrue(sft.isItType("Нормальная форма числа с плавающей запятой"));
		Assert.assertTrue(sft.isItType("Long"));
	}
	
	@Test
	public void one() {
		sft.add("Пример");
		String logShort = String.format(LOG_SHORT, 1);
		Assert.assertEquals(logShort, sfs.getShort());
		String logFull = String.format(LOG_FULL, 1, "6", "6");
		Assert.assertEquals(logFull, sfs.getFull());
	}
	
	@Test
	public void two() {
		sft.add("тестовое задание");
		sft.add("Long");
		String logShort = String.format(LOG_SHORT, 2);
		Assert.assertEquals(logShort, sfs.getShort());
		String logFull = String.format(LOG_FULL, 2, "4", "16");
		Assert.assertEquals(logFull, sfs.getFull());
	}
	
	@Test
	public void three() {
		sft.add(" n ");
		sft.add("тестовое задание");
		sft.add("Longg");
		String logShort = String.format(LOG_SHORT, 3);
		Assert.assertEquals(logShort, sfs.getShort());
		String logFull = String.format(LOG_FULL, 3, "3", "16");
		Assert.assertEquals(logFull, sfs.getFull());
	}
}

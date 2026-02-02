package filtering.types;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import filtering.types.StringFilterType.StringFilterStatistic;

public class StringFilterTypeTest {
	private String LOG_SHORT;
	private String LOG_FULL;
	private StringFilterType sft;
	private StringFilterStatistic sfs;
	
	@BeforeEach
	public void createFilter() {
		sft = new StringFilterType();
		sfs = (StringFilterStatistic)sft.getFilterStatistic();
		LOG_SHORT = FilterStatistic.LOG_SHORT.replaceFirst("%s", "strings");
		LOG_FULL = FilterStatistic.LOG_FULL.replaceFirst("%s", "strings");
	}
	
	@Test
	public void empty() {
		Assertions.assertEquals(Collections.emptyList(), sft.getAll());
		String logShort = String.format(LOG_SHORT, 0);
		Assertions.assertEquals(logShort, sfs.getShort());
		String logFull = String.format(LOG_FULL, 0, "-", "-");
		Assertions.assertEquals(logFull, sfs.getFull());
	}
	
	@Test
	public void wrongValues() {
		Assertions.assertFalse(sft.isItType(null));
		Assertions.assertFalse(sft.isItType(""));
	}
	
	@Test
	public void rightValues() {
		Assertions.assertTrue(sft.isItType("0"));
		Assertions.assertTrue(sft.isItType("    "));
		
		Assertions.assertTrue(sft.isItType("Lorem ipsum dolor sit amet"));
		Assertions.assertTrue(sft.isItType("Пример"));
		Assertions.assertTrue(sft.isItType("consectetur adipiscing"));
		Assertions.assertTrue(sft.isItType("тестовое задание"));
		Assertions.assertTrue(sft.isItType("Нормальная форма числа с плавающей запятой"));
		Assertions.assertTrue(sft.isItType("Long"));
	}
	
	@Test
	public void one() {
		sft.add("Пример");
		String logShort = String.format(LOG_SHORT, 1);
		Assertions.assertEquals(logShort, sfs.getShort());
		String logFull = String.format(LOG_FULL, 1, "6", "6");
		Assertions.assertEquals(logFull, sfs.getFull());
	}
	
	@Test
	public void two() {
		sft.add("тестовое задание");
		sft.add("Long");
		String logShort = String.format(LOG_SHORT, 2);
		Assertions.assertEquals(logShort, sfs.getShort());
		String logFull = String.format(LOG_FULL, 2, "4", "16");
		Assertions.assertEquals(logFull, sfs.getFull());
	}
	
	@Test
	public void three() {
		sft.add(" n ");
		sft.add("тестовое задание");
		sft.add("Longg");
		String logShort = String.format(LOG_SHORT, 3);
		Assertions.assertEquals(logShort, sfs.getShort());
		String logFull = String.format(LOG_FULL, 3, "3", "16");
		Assertions.assertEquals(logFull, sfs.getFull());
	}
	
	@Test
	public void many() {
		sft.add("Lorem ipsum dolor sit amet");
		sft.add("Пример");
		sft.add("consectetur adipiscing");
		sft.add("тестовое задание");
		sft.add("Нормальная форма числа с плавающей запятой");
		sft.add("Long");
		String logShort = String.format(LOG_SHORT, 6);
		Assertions.assertEquals(logShort, sfs.getShort());
		String logFull = String.format(LOG_FULL, 6, "4", "42");
		Assertions.assertEquals(logFull, sfs.getFull());
	}
}

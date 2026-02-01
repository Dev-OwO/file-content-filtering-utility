package filtering.types;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * фильтрация и сбор целочисленных значений
 */
public class IntegerFilterType implements FilterType {
	private IntegerFilterStatistic intFilterStat = new IntegerFilterStatistic();
	private List<String> valueList = new LinkedList<>();
	
	@Override
	public String getFilterName() {
		return "integers";
	}

	@Override
	public boolean isItType(String input) {
		try {
			getValueFromString(input);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	private long getValueFromString(String input) {
		return Long.parseLong(input);
	}

	@Override
	public void add(String input) {
		intFilterStat.add(input);
		valueList.add(input);
	}

	@Override
	public List<String> getAll() {
		return Collections.unmodifiableList(valueList);
	}

	@Override
	public FilterStatistic getFilterStatistic() {
		return intFilterStat;
	}

	/**
	 * сбор статистики по целочисленным значениям
	 */
	class IntegerFilterStatistic implements FilterNumberStatistic {
		private BigDecimal sum = BigDecimal.valueOf(0);
		private String minValueString;
		private long minValue;
		private String maxValueString;
		private long maxValue;
		
		void add(String input) {
			long i = getValueFromString(input);
			sum = sum.add(BigDecimal.valueOf(i));
			if(minValueString == null || i < minValue) {
				minValue = i;
				minValueString = input;
			}
			if(maxValueString == null || i > maxValue) {
				maxValue = i;
				maxValueString = input;
			}
		}

		@Override
		public String getTypeName() {
			return getFilterName();
		}
		
		@Override
		public long getCount() {
			return valueList.size();
		}
		
		@Override
		public String getMin() {
			return minValueString == null ?
					DEFAULT : minValueString;
		}
		
		@Override
		public String getMax() {
			return maxValueString == null ?
					DEFAULT : maxValueString;
		}
		
		@Override
		public String getSum() {
			return getCount() == 0 ? 
					DEFAULT : String.valueOf(sum);
		}
		
		@Override
		public String getAverage() {
			if(getCount() == 0)
				return DEFAULT;
			BigDecimal c = BigDecimal.valueOf(getCount());
			BigDecimal avr = sum.divide(c, 5, RoundingMode.HALF_UP);
			avr = avr.stripTrailingZeros();
			return avr.toString();
		}
		
	}
}

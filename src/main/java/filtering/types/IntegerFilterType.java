package filtering.types;

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
	public boolean isItType(String input) {
		try {
			getValueFromString(input);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	private int getValueFromString(String input) {
		return Integer.parseInt(input);
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
		private long sum = 0;
		private String minValueString;
		private int minValue;
		private String maxValueString;
		private int maxValue;
		
		void add(String input) {
			int i = getValueFromString(input);
			sum += i;
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
			double avr = (double)sum / getCount();
			String avrStr;
			if(avr % Math.round(avr) == 0)
				avrStr = String.valueOf((int)avr);
			else
				avrStr = String.valueOf(avr);
			return avrStr;
		}
		
	}
}

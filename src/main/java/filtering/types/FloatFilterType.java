package filtering.types;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * фильтрация и сбор вещественных чисел
 */
public class FloatFilterType implements FilterType {
	private FloatFilterStatistic floatFilterStat = new FloatFilterStatistic();
	private List<String> valueList = new LinkedList<>();
	
	@Override
	public String getFilterName() {
		return "floats";
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
	
	private double getValueFromString(String input) {
		double d = Double.parseDouble(input);
		if(d == Double.POSITIVE_INFINITY ||
				d == Double.NEGATIVE_INFINITY ||
				Double.isNaN(d))
			throw new NumberFormatException("Not float");
		return d;
	}

	@Override
	public void add(String input) {
		floatFilterStat.add(input);
		valueList.add(input);
	}

	@Override
	public List<String> getAll() {
		return Collections.unmodifiableList(valueList);
	}

	@Override
	public FilterStatistic getFilterStatistic() {
		return floatFilterStat;
	}
	
	/**
	 * сбор статистики по вещественным числам
	 */
	public class FloatFilterStatistic implements FilterNumberStatistic {
		private double sum = 0;
		private String minValueString;
		private double minValue;
		private String maxValueString;
		private double maxValue;
		
		void add(String input) {
			double d = getValueFromString(input);
			sum += d;
			if(minValueString == null || d < minValue) {
				minValue = d;
				minValueString = input;
			}
			if(maxValueString == null || d > maxValue) {
				maxValue = d;
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
			double avr = sum / getCount();
			String avrStr;
			if(avr % Math.round(avr) == 0)
				avrStr = String.valueOf((int)avr);
			else
				avrStr = String.valueOf(avr);
			return avrStr;
		}

	}

}

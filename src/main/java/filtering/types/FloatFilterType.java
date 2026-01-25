package filtering.types;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * фильтрация и сбор вещественных значений
 */
public class FloatFilterType implements FilterType {
	private FloatFilterStatistic floatFilterStat = new FloatFilterStatistic();
	private List<String> valueList = new LinkedList<>();

	@Override
	public boolean isItType(String input) {
		try {
			getValueFromString(input);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	private float getValueFromString(String input) {
		return Float.parseFloat(input);
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
	
	public class FloatFilterStatistic implements FilterNumberStatistic {
		private double sum = 0;
		private String minValueString;
		private float minValue;
		private String maxValueString;
		private float maxValue;
		
		void add(String input) {
			float f = getValueFromString(input);
			sum += f;
			if(minValueString == null || f < minValue) {
				minValue = f;
				minValueString = input;
			}
			if(maxValueString == null || f > maxValue) {
				maxValue = f;
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

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
			Integer.parseInt(input);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
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
			int i = Integer.parseInt(input);
			sum += i;
			if(i < minValue) {
				minValue = i;
				minValueString = input;
			}
			if(i > maxValue) {
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
			return minValueString;
		}
		
		@Override
		public String getMax() {
			return maxValueString;
		}
		
		@Override
		public String getSum() {
			return String.valueOf(sum);
		}
		
		@Override
		public String getAverage() {
			double avr = (double)sum / getCount();
			return  String.valueOf(avr);
		}
		
	}
}

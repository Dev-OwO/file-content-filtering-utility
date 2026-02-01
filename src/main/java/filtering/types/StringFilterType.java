package filtering.types;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * фильтрация и сбор строковых значений
 */
public class StringFilterType implements FilterType {
	private StringFilterStatistic strFilterStat = new StringFilterStatistic();
	private List<String> valueList = new LinkedList<>();
	
	@Override
	public String getFilterName() {
		return "strings";
	}

	@Override
	public boolean isItType(String input) {
		try {
			if(input == null || input.isEmpty())
				return false;
			getValueFromString(input);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	private String getValueFromString(String input) {
		return String.valueOf(input);
	}

	@Override
	public void add(String input) {
		strFilterStat.add(input);
		valueList.add(input);
	}

	@Override
	public List<String> getAll() {
		return Collections.unmodifiableList(valueList);
	}

	@Override
	public FilterStatistic getFilterStatistic() {
		return strFilterStat;
	}
	
	class StringFilterStatistic implements FilterStatistic {
		private long min = Long.MAX_VALUE;
		private long max = Long.MIN_VALUE;
		
		void add(String input) {
			if(input.length() > max)
				max = input.length();
			if(input.length() < min)
				min = input.length();
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
			return min == Long.MAX_VALUE ?
					DEFAULT : String.valueOf(min);
		}
		
		@Override
		public String getMax() {
			return max == Long.MIN_VALUE ?
					DEFAULT : String.valueOf(max);
		}
	}

}

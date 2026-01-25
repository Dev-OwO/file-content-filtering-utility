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
	public boolean isItType(String input) {
		try {
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

		@Override
		public long getCount() {
			return valueList.size();
		}
		
	}

}

package filtering;

import java.util.ArrayList;
import java.util.List;

import filtering.types.FilterType;

/**
 * фильтрация строковых данных по типам
 */
public class FilteringContent {
	private List<FilterType> filterList = new ArrayList<>();
	
	void addFilterType(FilterType ft) {
		filterList.add(ft);
	}
	
	void filterStrings(List<String> input) {
		for(String s: input) {
			for(FilterType ft: filterList) {
				if(!ft.isItType(s))
					continue;
				
				ft.add(s);
			}
		}
	}
	
	List<FilterType> getFilterList() {
		return filterList;
	}

}

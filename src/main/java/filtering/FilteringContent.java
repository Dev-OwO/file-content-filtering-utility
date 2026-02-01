package filtering;

import java.util.ArrayList;
import java.util.List;

import filtering.types.FilterStatistic;
import filtering.types.FilterType;

/**
 * Класс содержит фильтры данных разных типов
 * Добавляет данные в них и выводит статистику
 */
public class FilteringContent {
	private List<FilterType> filterList = new ArrayList<>();
	
	void addFilterType(FilterType ft) {
		filterList.add(ft);
	}
	
	/**
	 * отфильтровать входные данные по имеющимся типам
	 * @param input входные данные
	 */
	void filterStrings(List<String> input) {
		for(String s: input) {
			for(FilterType ft: filterList) {
				if(!ft.isItType(s))
					continue;
				
				ft.add(s);
			}
		}
	}
	
	/**
	 * отобразить в консоли краткую и полную статистику
	 * @param isShort вывести краткую статистику
	 * @param isFull вывести полную статистику
	 */
	void printStatistics(boolean isShort, boolean isFull) {
		for(FilterType ft: filterList) {
			FilterStatistic fs = ft.getFilterStatistic();
			if(isShort)
				fs.getShort();
			if(isFull)
				fs.getFull();
		}
	}
	
	List<FilterType> getFilterList() {
		return filterList;
	}
}

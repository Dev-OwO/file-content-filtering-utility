package filtering;

import java.util.ArrayList;
import java.util.List;

import filtering.types.FilterStatistic;
import filtering.types.FilterType;

/**
 * Класс содержит фильтры разных типов
 * Отправляет в них данные и выводит статистику
 */
public class FilteringContent {
	private List<FilterType> filterList = new ArrayList<>();
	
	/**
	 * добавить фильтр данных
	 * @param ft фильтр данных
	 */
	void addFilterType(FilterType ft) {
		filterList.add(ft);
	}
	
	/**
	 * отфильтровать входные данные по имеющимся типам
	 * @param input входные данные
	 */
	void filterStrings(List<String> input) {
		for(String s: input) {
			filterList.stream().filter(ft -> ft.isItType(s))
				.limit(1).forEach(ft -> ft.add(s));
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
				System.out.println(fs.getShort());
			if(isFull)
				System.out.println(fs.getFull());
		}
	}
	
	List<FilterType> getFilterList() {
		return filterList;
	}
}

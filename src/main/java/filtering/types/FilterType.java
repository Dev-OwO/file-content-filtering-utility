package filtering.types;

import java.util.List;

/**
 * интерфейс, определяющий фильтруемый тип данных
 */
public interface FilterType {
	
	/**
	 * получить название фильтра
	 * @return название фильтра
	 */
	String getFilterName();
	
	/**
	 * является ли заданное строковое представление заданным типом
	 * @param input строковое представление
	 * @return является или нет
	 */
	boolean isItType(String input);
	
	/**
	 * добавить строковое значение как этот тип данных
	 * @param input строковое представление
	 */
	void add(String input);
	
	/**
	 * получить все строковые представления со значением этого типа
	 * @return список строк
	 */
	List<String> getAll();
	
	/**
	 * получить объект с данными статистики
	 * @return данные статистики
	 */
	FilterStatistic getFilterStatistic();

}

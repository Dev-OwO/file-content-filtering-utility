package filtering.types;

/**
 * интерфейс определяет поведение сбора статистики для числовых значений
 */
public interface FilterNumberStatistic extends FilterStatistic {
	public static final String LOG_FULL = "%s: Кол-во = %d, минимальное = %s, максимальное = %s, сумма = %s, среднее = %s";
	
	String getSum();
	
	String getAverage();
	
	default String getFull() {
		return String.format(LOG_FULL, getTypeName(), getCount(), getMin(), getMax(),
				getSum(), getAverage());
	}

}

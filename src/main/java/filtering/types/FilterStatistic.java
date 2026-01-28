package filtering.types;

/**
 * интерфейс определяет поведение сбора статистики
 */
public interface FilterStatistic {
	public static final String DEFAULT = "-";
	public static final String LOG_SHORT = "Кол-во = %d";
	public static final String LOG_FULL = "Кол-во = %d, минимальное = %s, максимальное = %s";

	long getCount();
	
	String getMin();
	
	String getMax();
	
	default String getShort() {
		return String.format(LOG_SHORT, getCount());
	}
	
	default String getFull() {
		return String.format(LOG_FULL, getCount(), getMin(), getMax());
	}
}

package filtering.types;

/**
 * интерфейс определяет поведение сбора статистики
 */
public interface FilterStatistic {
	public static final String DEFAULT = "-";
	public static final String LOG_SHORT = "%s: Кол-во = %d";
	public static final String LOG_FULL = "%s: Кол-во = %d, минимальное = %s, максимальное = %s";
	
	String getTypeName();

	long getCount();
	
	String getMin();
	
	String getMax();
	
	default String getShort() {
		return String.format(LOG_SHORT, getTypeName(), getCount());
	}
	
	default String getFull() {
		return String.format(LOG_FULL, getTypeName(), getCount(), getMin(), getMax());
	}
}

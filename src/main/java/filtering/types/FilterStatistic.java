package filtering.types;

/**
 * интерфейс определяет поведение сбора статистики
 */
public interface FilterStatistic {
	public static final String DEFAULT = "-";
	public static final String LOG_SHORT = "Кол-во = %d";

	long getCount();
	
	default String getShort() {
		return String.format(LOG_SHORT, getCount());
	}
	
	default String getFull() {
		return getShort();
	}
}

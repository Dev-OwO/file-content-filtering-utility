package filtering.types;

/**
 * интерфейс определяет поведение сбора статистики
 */
public interface FilterStatistic {
	public static final String DEFAULT = "-";

	long getCount();
	
	default String getShort() {
		return String.format("Кол-во = %d", getCount());
	}
	
	default String getFull() {
		return String.format("Кол-во = %d", getCount());
	}
}

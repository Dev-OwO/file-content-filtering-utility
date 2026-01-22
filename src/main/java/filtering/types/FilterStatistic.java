package filtering.types;

/**
 * интерфейс определяет поведение сбора статистики
 */
public interface FilterStatistic {

	long getCount();
	
	String getMin();
	
	String getMax();
	
	default String getShort() {
		return String.format("Кол-во = %d", getCount());
	}
	
	default String getFull() {
		return String.format("Кол-во = %d, минимальное = %s, максимальное = %s",
				getCount(), getMin(), getMax());
	}
}

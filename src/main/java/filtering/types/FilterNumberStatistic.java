package filtering.types;

public interface FilterNumberStatistic extends FilterStatistic {
	public static final String LOG_FULL = "Кол-во = %d, минимальное = %s, максимальное = %s, сумма = %s, среднее = %s";
	
	String getSum();
	
	String getAverage();
	
	default String getFull() {
		return String.format(LOG_FULL, getCount(), getMin(), getMax(),
				getSum(), getAverage());
	}

}

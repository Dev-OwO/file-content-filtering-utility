package filtering.types;

public interface FilterNumberStatistic extends FilterStatistic {
	
	String getSum();
	
	String getAverage();
	
	default String getFull() {
		return String.format("Кол-во = %d, минимальное = %s, максимальное = %s, сумма = %s, среднее = %s",
				getCount(), getMin(), getMax(), getSum(), getAverage()
				);
	}

}

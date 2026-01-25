package filtering;

import filtering.types.FloatFilterType;
import filtering.types.IntegerFilterType;
import filtering.types.StringFilterType;

/**
 * класс запуска утилиты по фильтрации содержимого файлов
 */
public class FileContentFiltering {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	static FilteringContent getIntFloatString() {
		FilteringContent fc = new FilteringContent();
		// порядок следования важен
		fc.addFilterType(new IntegerFilterType());
		fc.addFilterType(new FloatFilterType());
		fc.addFilterType(new StringFilterType());
		return fc;
	}

}

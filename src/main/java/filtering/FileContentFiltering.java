package filtering;

import java.util.List;

import filtering.types.FilterStatistic;
import filtering.types.FilterType;
import filtering.types.FloatFilterType;
import filtering.types.IntegerFilterType;
import filtering.types.StringFilterType;

/**
 * класс запуска утилиты по фильтрации содержимого файлов
 */
public class FileContentFiltering {

	public static void main(String[] args) {
		ArgsReader ar = new ArgsReader();
		ar.read(args);
		List<String> fileNames = ar.getFiles();
		if(fileNames.isEmpty()) {
			System.out.println("Нет файлов для обработки");
			return;
		}
		
		FileManager fileManager = new FileManager(ar.getPath(), ar.getPrefix(), ar.isAddToExist());
		List<String> content = fileManager.getContentFromFiles(fileNames);
		if(content.isEmpty()) {
			System.out.println("Нет данных для обработки");
			return;
		}
		
		FilteringContent fc = getIntFloatString();
		fc.filterStrings(content);
		
		List<FilterType> filterList = fc.getFilterList();
		fileManager.saveToFiles(filterList);
		
		for(FilterType ft: filterList) {
			FilterStatistic fs = ft.getFilterStatistic();
			if(ar.isShowShortStatistic())
				fs.getShort();
			if(ar.isShowFullStatistic())
				fs.getFull();
		}
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

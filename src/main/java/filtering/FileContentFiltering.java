package filtering;

import java.util.List;

import filtering.types.FilterType;
import filtering.types.FloatFilterType;
import filtering.types.IntegerFilterType;
import filtering.types.StringFilterType;

/**
 * класс запуска утилиты по фильтрации содержимого файлов
 */
public class FileContentFiltering {

	public static void main(String[] args) {
		ArgsReader ar;
		try {
			ar = new ArgsReader();
			ar.read(args);
		} catch(Exception exc) {
			System.out.println("При разборе аргументов командной строки произошла ошибка: " + exc.getMessage());
			System.out.println("Убедитесь что вы правильно задали аргументы: " + String.join(" ", args));
			return;
		}
		
		List<String> fileNames = ar.getFiles();
		if(fileNames == null || fileNames.isEmpty()) {
			System.out.println("Нет файлов для обработки");
			return;
		}
		
		FileManager fileManager;
		List<String> content;
		try {
			fileManager = new FileManager(ar.getPath(), ar.getPrefix(), ar.isAddToExist());
			content = fileManager.getContentFromFiles(fileNames);
		} catch(Exception exc) {
			System.out.println("При получении данных из файлов произошла ошибка: " + exc.getMessage());
			System.out.println("Проверьте файлы с данными: " + String.join(" ", fileNames));
			return;
		}
		
		if(content == null || content.isEmpty()) {
			System.out.println("Нет данных для обработки");
			return;
		}
		
		FilteringContent fc = getIntFloatString();
		try {
			fc.filterStrings(content);
		} catch(Exception exc) {
			System.out.println("При фильтрации данных произошла ошибка: " + exc.getMessage());
			return;
		}
		
		List<FilterType> filterList = fc.getFilterList();
		try {
			fileManager.saveToFiles(filterList);
			fc.printStatistics(ar.isShowShortStatistic(), ar.isShowFullStatistic());
		} catch(Exception exc) {
			System.out.println("При сохранении и выводе статистики произошла ошибка: " + exc.getMessage());
			return;
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

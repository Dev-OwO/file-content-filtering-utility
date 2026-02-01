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
	private ArgsReader ar;
	private FileManager fileManager;
	private FilteringContent fc;

	public static void main(String[] args) {
		FileContentFiltering fcf = new FileContentFiltering();
		try {
			fcf.filter(args);
		} catch(Exception exc) {
			System.out.println(exc.getMessage());
		}
	}
	
	/**
	 * основное выполнение
	 */
	private void filter(String[] args) {
		readArgs(args);
		List<String> fileNames = ar.getFiles();
		if(fileNames == null || fileNames.isEmpty()) {
			System.out.println("Нет файлов для обработки");
			return;
		}
		List<String> content = getContent(fileNames);
		if(content == null || content.isEmpty()) {
			System.out.println("Нет данных для обработки");
			return;
		}
		filterContent(content);
		save();
	}
	
	private void readArgs(String[] args) {
		try {
			ar = new ArgsReader();
			ar.read(args);
		} catch(Exception exc) {
			String msg = "При разборе аргументов командной строки произошла ошибка: " + exc.getMessage();
			if(args != null)
				msg += "\n" + "Убедитесь что вы правильно задали аргументы: " + String.join(" ", args);
			throw new RuntimeException(msg);
		}
		List<String> wa = ar.getWrongArgs();
		if(!wa.isEmpty()) {
			System.out.println("Есть неизвестные аргументы, они будут проигнорированы: " +
					String.join(" ", wa));
		}
	}
	
	private List<String> getContent(List<String> fileNames) {
		try {
			fileManager = new FileManager(ar.getPath(), ar.getPrefix(), ar.isAddToExist());
			return fileManager.getContentFromFiles(fileNames);
		} catch(Exception exc) {
			String msg = "При получении данных из файлов произошла ошибка: " + exc.getMessage();
			msg += "\n" + "Проверьте файлы с данными: " + String.join(" ", fileNames);
			throw new RuntimeException(msg);
		}
	}
	
	private void filterContent(List<String> content) {
		fc = getIntFloatString();
		try {
			fc.filterStrings(content);
		} catch(Exception exc) {
			String msg = "При фильтрации данных произошла ошибка: " + exc.getMessage();
			throw new RuntimeException(msg);
		}
	}
	
	private void save() {
		List<FilterType> filterList = fc.getFilterList();
		try {
			fileManager.saveToFiles(filterList);
			fc.printStatistics(ar.isShowShortStatistic(), ar.isShowFullStatistic());
		} catch(Exception exc) {
			String msg = "При сохранении и выводе статистики произошла ошибка: " + exc.getMessage();
			throw new RuntimeException(msg);
		}
	}
	
	/**
	 * получить объект фильтрации данных с типами: integer, float, string
	 * @return объект фильтрации данных
	 */
	static FilteringContent getIntFloatString() {
		FilteringContent fc = new FilteringContent();
		// порядок следования важен
		fc.addFilterType(new IntegerFilterType());
		fc.addFilterType(new FloatFilterType());
		fc.addFilterType(new StringFilterType());
		return fc;
	}
}

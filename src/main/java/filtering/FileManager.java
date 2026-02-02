package filtering;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import filtering.types.FilterType;

/**
 * класс для чтения и записи строк в файлах
 */
class FileManager {
	String path = ".";
	String prefix = "";
	boolean addToExist = false;
	
	FileManager(String path, String prefix, boolean addToExist) {
		if(path != null && !path.isEmpty())
			this.path = path;
		if(prefix != null && !prefix.isEmpty())
			this.prefix = prefix;
		this.addToExist = addToExist;
	}
	
	/**
	 * получение содержимого файлов
	 * @param fileNames список имен файлов
	 * @return общее содержимое всех файлов
	 */
	List<String> getContentFromFiles(List<String> fileNames) {
		List<File> files = fileNames.stream().map(n -> new File(path + File.separator + n))
				.filter(f -> f.exists()).toList();
		
		List<String> content = new LinkedList<>();
		for(File f: files) {
			try(FileReader fr = new FileReader(f);
					BufferedReader br = new BufferedReader(fr)) {
				String line;
				while ((line = br.readLine()) != null) {
					content.add(line);
				}
			} catch(IOException e) {
				System.out.println("Ошибка чтения файла " + f.getAbsolutePath() + ": " + e.getMessage());
			}
		}
		return content;
	}
	
	/**
	 * Сохранение отфильтрованных данных по отдельным файлам
	 * @param filterList список фильтров с данными
	 */
	void saveToFiles(List<FilterType> filterList) {
		for(FilterType ft: filterList) {
			List<String> content = ft.getAll();
			if(content.isEmpty())
				continue;
			
			String name = ft.getFilterName();
			createFile(name, content);
		}
	}

	/**
	 * создание файла с отфильтрованными данными одного типа
	 */
	private void createFile(String name, List<String> content) {
		String filePath = path + File.separator + prefix + name + ".txt";
		File f = new File(filePath);
		try(FileWriter fw = new FileWriter(f, addToExist);
				BufferedWriter bw = new BufferedWriter(fw)) {
			for(String c: content) {
				bw.write(c + "\n");
			}
		} catch (IOException e) {
			System.out.println("Ошибка записи в файл " + f.getAbsolutePath() + ": " + e.getMessage());
		}
	}
}

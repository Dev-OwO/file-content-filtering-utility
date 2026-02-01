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
	String path = "";
	String prefix = "";
	boolean addToExist = false;
	
	FileManager(String path, String prefix, boolean addToExist) {
		this.path = path;
		if(path.isEmpty())
			this.path = ".";
		this.prefix = prefix;
		this.addToExist = addToExist;
	}
	
	/**
	 * получение содержимого файлов
	 * @param fileNames список имен файлов
	 * @return общее содержимое всех файлов
	 */
	List<String> getContentFromFiles(List<String> fileNames) {
		List<String> content = new LinkedList<>();
		for(String fn: fileNames) {
			File f = new File(path + File.separator + fn);
			if(!f.exists())
				continue;
			
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
				bw.write(c);
			}
		} catch (IOException e) {
			System.out.println("Ошибка записи в файл " + f.getAbsolutePath() + ": " + e.getMessage());
		}
	}
}

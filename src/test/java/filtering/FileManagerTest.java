package filtering;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import filtering.types.StringFilterType;

public class FileManagerTest {
	private static final String testPostfix = "_for_test";
	FileManager fileManager;
	
	@Test
	public void emptyRead() {
		fileManager = new FileManager(null, null, false);
		List<String> content = fileManager.getContentFromFiles(Collections.emptyList());
		Assert.assertEquals(Collections.emptyList(), content);
	}
	
	@Test
	public void oneFileRead() {
		String testFileName = "text" + testPostfix + ".txt";
		String testFilePath = "." + File.separator + testFileName;
		List<String> contentTest = Arrays.asList("test1", "tre sdf", "356 4536");
		createFile(testFilePath, contentTest);
		
		fileManager = new FileManager(null, null, false);
		List<String> content = fileManager.getContentFromFiles(Arrays.asList(testFileName));
		Assert.assertEquals(contentTest, content);
	}
	
	@Test
	public void twoFilesRead() {
		String testFileName = "text" + testPostfix + ".txt";
		String testFilePath = "." + File.separator + testFileName;
		List<String> contentTest = Arrays.asList("10.009", "test1", "tre sdf", "356 4536");
		createFile(testFilePath, contentTest);
		String testFileName2 = "2text2" + testPostfix + ".txt";
		String testFilePath2 = "." + File.separator + testFileName2;
		List<String> contentTest2 = Arrays.asList("222", "", "sdf segtwreg sd gfdsg d", "567 bags", "45,76");
		createFile(testFilePath2, contentTest2);
		
		
		fileManager = new FileManager(null, null, false);
		List<String> content = fileManager.getContentFromFiles(Arrays.asList(testFileName, testFileName2));
		List<String> allContent = new LinkedList<>();
		allContent.addAll(contentTest);
		allContent.addAll(contentTest2);
		Assert.assertEquals(allContent, content);
	}
	
	@Test
	public void oneTypeWrite() {
		fileManager = new FileManager(null, null, false);
		StringFilterType sft = new StringFilterType();
		sft.add("qwerty");
		sft.add("600 + 677");
		sft.add("0000 ytghhj");
		fileManager.saveToFiles(Arrays.asList(sft));
		
		List<String> content = getContentFromFile("." + File.separator + "string.txt");
		Assert.assertEquals(sft.getAll(), content);
	}
	
	@Test
	public void oneTypeRewrite() {
		fileManager = new FileManager(null, null, false);
		StringFilterType sft = new StringFilterType();
		sft.add("qwerty");
		sft.add("600 + 677");
		sft.add("0000 ytghhj");
		fileManager.saveToFiles(Arrays.asList(sft));
		sft.add("wett sdgdfh цыеппы кп hj");
		fileManager.saveToFiles(Arrays.asList(sft));
		
		List<String> content = getContentFromFile("." + File.separator + "string.txt");
		Assert.assertEquals(sft.getAll(), content);
	}
	
//	@Test
//	public void oneTypeWriteDouble() {
//		fileManager = new FileManager(null, null, true);
//		StringFilterType sft = new StringFilterType();
//		sft.add("qwerty");
//		sft.add("600 + 677");
//		sft.add("0000 ytghhj");
//		fileManager.saveToFiles(Arrays.asList(sft));
//		fileManager.saveToFiles(Arrays.asList(sft));
//		
//		List<String> content = getContentFromFile("." + File.separator + "string.txt");
//		List<String> allContent = new LinkedList<>();
//		allContent.addAll(sft.getAll());
//		allContent.addAll(sft.getAll());
//		Assert.assertEquals(allContent, content);
//	}
//	
//	@After
//	public void deleteCreatedFiles() {
//		File workspace = new File("");
//		for(File f: workspace.listFiles()) {
//			String fn = f.getName();
//			if(!fn.contains(testPostfix))
//				continue;
//			f.delete();
//		}
//	}
	
	/**
	 * создание файла
	 */
	private void createFile(String testFilePath, List<String> content) {
		File f = new File(testFilePath);
		try(FileWriter fw = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(fw)) {
			for(String c: content) {
				bw.write(c + "\n");
			}
		} catch (IOException e) {
			System.out.println("Ошибка записи в файл " + f.getAbsolutePath() + ": " + e.getMessage());
		}
	}
	
	/**
	 * получение содержимого файлов
	 */
	List<String> getContentFromFile(String fileName) {
		List<String> content = new LinkedList<>();
		File f = new File(fileName);
		try(FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr)) {
			String line;
			while ((line = br.readLine()) != null) {
				content.add(line);
			}
		} catch(IOException e) {
			System.out.println("Ошибка чтения файла " + f.getAbsolutePath() + ": " + e.getMessage());
		}
		return content;
	}
}

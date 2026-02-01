package filtering;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class FileManagerReadTest {
	private static final String testPostfix = "_for_test";
	private FileManager fileManager;
	
	@Test
	public void emptyRead() {
		fileManager = new FileManager(null, null, false);
		List<String> content = fileManager.getContentFromFiles(Collections.emptyList());
		Assert.assertEquals(Collections.emptyList(), content);
	}
	
	@Test
	public void oneUnexistFileRead() {
		String testFileName = "text" + testPostfix + ".txt";
		
		fileManager = new FileManager(null, null, false);
		List<String> content = fileManager.getContentFromFiles(Arrays.asList(testFileName));
		Assert.assertEquals(Collections.emptyList(), content);
	}
	
	@Test
	public void oneEmptyFileRead() {
		String testFileName = "text" + testPostfix + ".txt";
		String testFilePath = "." + File.separator + testFileName;
		createFile(testFilePath, Collections.emptyList());
		
		fileManager = new FileManager(null, null, false);
		List<String> content = fileManager.getContentFromFiles(Arrays.asList(testFileName));
		Assert.assertEquals(Collections.emptyList(), content);
	}
	
	@Test
	public void oneUnexistFileReadWrong() {
		String testFileName = "te*?xt" + testPostfix + ".txt";
		
		fileManager = new FileManager(null, null, false);
		List<String> content = fileManager.getContentFromFiles(Arrays.asList(testFileName));
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
	public void oneFileReadAnotherType() {
		String testFileName = "text" + testPostfix + ".log";
		String testFilePath = "." + File.separator + testFileName;
		List<String> contentTest = Arrays.asList("test1", "tre sdf", "356 4536");
		createFile(testFilePath, contentTest);
		
		fileManager = new FileManager(null, null, false);
		List<String> content = fileManager.getContentFromFiles(Arrays.asList(testFileName));
		Assert.assertEquals(contentTest, content);
	}
	
	@After
	public void deleteCreatedFiles() {
		File workspace = new File(".");
		for(File f: workspace.listFiles()) {
			String fn = f.getName();
			if(fn.contains(testPostfix))
				f.delete();
		}
	}
	
	/**
	 * создание файла
	 */
	public static void createFile(String testFilePath, List<String> content) {
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
}

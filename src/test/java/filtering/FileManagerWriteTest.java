package filtering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import filtering.types.IntegerFilterType;
import filtering.types.StringFilterType;

public class FileManagerWriteTest {
	private static final String testStringFile = "strings.txt";
	private static final String testIntegerFile = "integers.txt";
	private FileManager fileManager;
	private String anotherPath;
	private StringFilterType sft;
	
	@Before
	public void getAnotherPath() {
		anotherPath = System.getProperty("java.io.tmpdir");
		sft = getTestStringFilterType();
	}
	
	private StringFilterType getTestStringFilterType() {
		StringFilterType sft = new StringFilterType();
		sft.add("qwerty");
		sft.add("600 + 677");
		sft.add("0000 ytghhj");
		return sft;
	}
	
	@Test
	public void oneTypeWrite() {
		fileManager = new FileManager(null, null, false);
		fileManager.saveToFiles(Arrays.asList(sft));
		
		List<String> content = getContentFromFile("." + File.separator + testStringFile);
		Assert.assertEquals(sft.getAll(), content);
	}
	
	@Test
	public void oneTypeRewrite() {
		fileManager = new FileManager(null, null, false);
		fileManager.saveToFiles(Arrays.asList(sft));
		sft.add("wett sdgdfh цыеппы кп hj");
		fileManager.saveToFiles(Arrays.asList(sft));
		
		List<String> content = getContentFromFile("." + File.separator + testStringFile);
		Assert.assertEquals(sft.getAll(), content);
	}
	
	@Test
	public void oneTypeWriteDouble() {
		fileManager = new FileManager(null, null, true);
		fileManager.saveToFiles(Arrays.asList(sft));
		fileManager.saveToFiles(Arrays.asList(sft));
		
		List<String> content = getContentFromFile("." + File.separator + testStringFile);
		List<String> allContent = new LinkedList<>();
		allContent.addAll(sft.getAll());
		allContent.addAll(sft.getAll());
		Assert.assertEquals(allContent, content);
	}
	
	@Test
	public void oneTypeWritePrefix() {
		fileManager = new FileManager(null, "result_", false);
		fileManager.saveToFiles(Arrays.asList(sft));
		
		List<String> content = getContentFromFile("." + File.separator + "result_" + testStringFile);
		Assert.assertEquals(sft.getAll(), content);
	}
	
	@Test
	public void oneTypeWritePath() {
		fileManager = new FileManager(anotherPath, null, false);
		fileManager.saveToFiles(Arrays.asList(sft));
		
		List<String> content = getContentFromFile(anotherPath + File.separator + testStringFile);
		Assert.assertEquals(sft.getAll(), content);
	}
	
	@Test
	public void oneAndEmptyTypesWrite() {
		IntegerFilterType ift = new IntegerFilterType();
		
		fileManager = new FileManager(null, null, false);
		fileManager.saveToFiles(Arrays.asList(sft, ift));
		
		List<String> content = getContentFromFile("." + File.separator + testStringFile);
		Assert.assertEquals(sft.getAll(), content);
		File fileWithIntegers = new File("." + File.separator + testIntegerFile);
		Assert.assertFalse(fileWithIntegers.exists());
	}
	
	@Test
	public void twoTypesWrite() {
		IntegerFilterType ift = new IntegerFilterType();
		ift.add("100");
		ift.add("-200100");
		
		fileManager = new FileManager(anotherPath, "result_", true);
		fileManager.saveToFiles(Arrays.asList(sft, ift));
		
		List<String> content = getContentFromFile(anotherPath + File.separator + "result_" + testStringFile);
		Assert.assertEquals(sft.getAll(), content);
		List<String> content2 = getContentFromFile(anotherPath + File.separator + "result_" + testIntegerFile);
		Assert.assertEquals(ift.getAll(), content2);
	}
	
	@Test
	public void oneTypeWriteWrongPrefix() {
		fileManager = new FileManager(null, "re*sult?_", false);
		fileManager.saveToFiles(Arrays.asList(sft));
	}
	
	@Test
	public void oneTypeWriteWrongPath() {
		fileManager = new FileManager(anotherPath + File.separator + "new*dir?new", null, false);
		fileManager.saveToFiles(Arrays.asList(sft));
	}
	
	@After
	public void deleteCreatedFiles() {
		File workspace = new File(".");
		File temp = new File(anotherPath);
		for(File d: Arrays.asList(workspace, temp)) {
			for(File f: d.listFiles()) {
				String fn = f.getName();
				if(fn.endsWith(testStringFile))
					f.delete();
				if(fn.endsWith(testIntegerFile))
					f.delete();
			}
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

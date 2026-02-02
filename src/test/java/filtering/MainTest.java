package filtering;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * тестирование всего приложения
 */
public class MainTest {
	private static final String prefixSample = "sample-";
	
	@Test
	public void test() {
		String testFileName1 = "in1.txt";
		String testFilePath1 = "." + File.separator + testFileName1;
		List<String> contentTest1 = Arrays.asList(
				"Lorem ipsum dolor sit amet",
				"45",
				"Пример",
				"3.1415",
				"consectetur adipiscing",
				"-0.001",
				"тестовое задание",
				"100500");
		FileManagerReadTest.createFile(testFilePath1, contentTest1);
		
		String testFileName2 = "in2.txt";
		String testFilePath2 = "." + File.separator + testFileName2;
		List<String> contentTest2 = Arrays.asList(
				"Нормальная форма числа с плавающей запятой",
				"1.528535047E-25",
				"Long",
				"1234567890123456789");
		FileManagerReadTest.createFile(testFilePath2, contentTest2);
		
		FileContentFiltering.main("-s -a -p sample- in1.txt in2.txt".split(" "));
		
		List<String> content11 = Arrays.asList("45",
				"100500",
				"1234567890123456789");
		List<String> content1 = FileManagerWriteTest.getContentFromFile("." + File.separator + "sample-integers.txt");
		Assertions.assertEquals(content11, content1);
		
		List<String> content22 = Arrays.asList("3.1415",
				"-0.001",
				"1.528535047E-25");
		List<String> content2 = FileManagerWriteTest.getContentFromFile("." + File.separator + "sample-floats.txt");
		Assertions.assertEquals(content22, content2);
		
		List<String> content33 = Arrays.asList("Lorem ipsum dolor sit amet",
				"Пример",
				"consectetur adipiscing",
				"тестовое задание",
				"Нормальная форма числа с плавающей запятой",
				"Long");
		List<String> content3 = FileManagerWriteTest.getContentFromFile("." + File.separator + "sample-strings.txt");
		Assertions.assertEquals(content33, content3);
	}
	
	@Test
	public void testEmpty() {
		FileContentFiltering.main(new String[0]);
		FileContentFiltering.main("-s -a -p sample-".split(" "));
		FileContentFiltering.main(null);
		FileContentFiltering.main("-z -x -c sample-".split(" "));
	}
	
	@Test
	public void checkInTxtMatch() {
		Assertions.assertTrue("in1.txt".matches(".*in.\\.txt"));
		Assertions.assertTrue("./in1.txt".matches(".*in.\\.txt"));
		Assertions.assertTrue("/tmp/in2.txt".matches(".*in\\d\\.txt"));
		Assertions.assertFalse("/tmp/ina.txt".matches(".*in\\d\\.txt"));
	}
	
	@AfterEach
	public void deleteCreatedFiles() {
		File workspace = new File(".");
		for(File f: workspace.listFiles()) {
			String fn = f.getName();
			if(fn.contains(prefixSample))
				f.delete();
			if(fn.matches(".*in\\d\\.txt"))
				f.delete();
		}
	}
}

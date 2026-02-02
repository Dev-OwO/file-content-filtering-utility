package filtering;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArgsParserTest {
	private ArgsParser argsReader;
	
	@BeforeEach
	public void start() {
		argsReader = new ArgsParser();
	}
	
	@Test
	public void files() {
		String[] a = {"int.txt", "float.txt", "string.txt"};
		argsReader.parse(a);
		
		Assertions.assertEquals("", argsReader.getPath());
		Assertions.assertEquals("", argsReader.getPrefix());
		Assertions.assertFalse(argsReader.isAddToExist());
		Assertions.assertFalse(argsReader.isShowShortStatistic());
		Assertions.assertFalse(argsReader.isShowFullStatistic());
		Assertions.assertEquals(Arrays.asList(a), argsReader.getFiles());
	}
	
	@Test
	public void all() {
		String[] a = {"-o", "my_job", "-p", "file_", "-a", "-s", "-f", "int.txt", "float.txt", "string.txt"};
		String[] f = {"int.txt", "float.txt", "string.txt"};
		argsReader.parse(a);
		
		Assertions.assertEquals("my_job", argsReader.getPath());
		Assertions.assertEquals("file_", argsReader.getPrefix());
		Assertions.assertTrue(argsReader.isAddToExist());
		Assertions.assertTrue(argsReader.isShowShortStatistic());
		Assertions.assertTrue(argsReader.isShowFullStatistic());
		Assertions.assertEquals(Arrays.asList(f), argsReader.getFiles());
	}
	
	@Test
	public void wrongArgs() {
		String[] a = {"-o", "-p", "-b", "-c"};
		argsReader.parse(a);
		
		Assertions.assertEquals("", argsReader.getPath());
		Assertions.assertEquals("", argsReader.getPrefix());
		Assertions.assertFalse(argsReader.isAddToExist());
		Assertions.assertFalse(argsReader.isShowShortStatistic());
		Assertions.assertFalse(argsReader.isShowFullStatistic());
		Assertions.assertEquals(Collections.emptyList(), argsReader.getFiles());
		Assertions.assertEquals(Arrays.asList(a), argsReader.getWrongArgs());
	}
	
	@Test
	public void filesAnotherPosition() {
		String[] a = {"-o", "my_job", "int.txt", "-p", "file_", "float.txt", "-a", "string.txt", "-s", "-f", "string2.txt"};
		String[] f = {"int.txt", "float.txt", "string.txt", "string2.txt"};
		argsReader.parse(a);
		
		Assertions.assertEquals("my_job", argsReader.getPath());
		Assertions.assertEquals("file_", argsReader.getPrefix());
		Assertions.assertTrue(argsReader.isAddToExist());
		Assertions.assertTrue(argsReader.isShowShortStatistic());
		Assertions.assertTrue(argsReader.isShowFullStatistic());
		Assertions.assertEquals(Arrays.asList(f), argsReader.getFiles());
	}
	
	
}

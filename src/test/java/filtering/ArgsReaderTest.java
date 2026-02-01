package filtering;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArgsReaderTest {
	private ArgsReader argsReader;
	
	@Before
	public void start() {
		argsReader = new ArgsReader();
	}
	
	@Test
	public void files() {
		String[] a = {"int.txt", "float.txt", "string.txt"};
		argsReader.read(a);
		
		Assert.assertEquals("", argsReader.getPath());
		Assert.assertEquals("", argsReader.getPrefix());
		Assert.assertFalse(argsReader.isAddToExist());
		Assert.assertFalse(argsReader.isShowShortStatistic());
		Assert.assertFalse(argsReader.isShowFullStatistic());
		Assert.assertEquals(Arrays.asList(a), argsReader.getFiles());
	}
	
	@Test
	public void all() {
		String[] a = {"-o", "my_job", "-p", "file_", "-a", "-s", "-f", "int.txt", "float.txt", "string.txt"};
		String[] f = {"int.txt", "float.txt", "string.txt"};
		argsReader.read(a);
		
		Assert.assertEquals("my_job", argsReader.getPath());
		Assert.assertEquals("file_", argsReader.getPrefix());
		Assert.assertTrue(argsReader.isAddToExist());
		Assert.assertTrue(argsReader.isShowShortStatistic());
		Assert.assertTrue(argsReader.isShowFullStatistic());
		Assert.assertEquals(Arrays.asList(f), argsReader.getFiles());
	}
	
	@Test
	public void wrongArgs() {
		String[] a = {"-o", "-p", "-b", "-c"};
		argsReader.read(a);
		
		Assert.assertEquals("", argsReader.getPath());
		Assert.assertEquals("", argsReader.getPrefix());
		Assert.assertFalse(argsReader.isAddToExist());
		Assert.assertFalse(argsReader.isShowShortStatistic());
		Assert.assertFalse(argsReader.isShowFullStatistic());
		Assert.assertEquals(Collections.emptyList(), argsReader.getFiles());
		Assert.assertEquals(Arrays.asList(a), argsReader.getWrongArgs());
	}
	
	@Test
	public void filesAnotherPosition() {
		String[] a = {"-o", "my_job", "int.txt", "-p", "file_", "float.txt", "-a", "string.txt", "-s", "-f", "string2.txt"};
		String[] f = {"int.txt", "float.txt", "string.txt", "string2.txt"};
		argsReader.read(a);
		
		Assert.assertEquals("my_job", argsReader.getPath());
		Assert.assertEquals("file_", argsReader.getPrefix());
		Assert.assertTrue(argsReader.isAddToExist());
		Assert.assertTrue(argsReader.isShowShortStatistic());
		Assert.assertTrue(argsReader.isShowFullStatistic());
		Assert.assertEquals(Arrays.asList(f), argsReader.getFiles());
	}
	
	
}

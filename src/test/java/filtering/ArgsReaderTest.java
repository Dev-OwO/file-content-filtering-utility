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
		
		Assert.assertEquals("", argsReader.path);
		Assert.assertEquals("", argsReader.prefix);
		Assert.assertFalse(argsReader.addToExist);
		Assert.assertFalse(argsReader.showShortStatistic);
		Assert.assertFalse(argsReader.showFullStatistic);
		Assert.assertEquals(Arrays.asList(a), argsReader.files);
	}
	
	@Test
	public void all() {
		String[] a = {"-o", "my_job", "-p", "file_", "-a", "-s", "-f", "int.txt", "float.txt", "string.txt"};
		String[] f = {"int.txt", "float.txt", "string.txt"};
		argsReader.read(a);
		
		Assert.assertEquals("my_job", argsReader.path);
		Assert.assertEquals("file_", argsReader.prefix);
		Assert.assertTrue(argsReader.addToExist);
		Assert.assertTrue(argsReader.showShortStatistic);
		Assert.assertTrue(argsReader.showFullStatistic);
		Assert.assertEquals(Arrays.asList(f), argsReader.files);
	}
	
	@Test
	public void wrongArgs() {
		String[] a = {"-o", "-p", "-b", "-c"};
		argsReader.read(a);
		
		Assert.assertEquals("", argsReader.path);
		Assert.assertEquals("", argsReader.prefix);
		Assert.assertFalse(argsReader.addToExist);
		Assert.assertFalse(argsReader.showShortStatistic);
		Assert.assertFalse(argsReader.showFullStatistic);
		Assert.assertEquals(Collections.emptyList(), argsReader.files);
		Assert.assertEquals(Arrays.asList(a), argsReader.wrongArgs);
	}
	
	@Test
	public void filesAnotherPosition() {
		String[] a = {"-o", "my_job", "int.txt", "-p", "file_", "float.txt", "-a", "string.txt", "-s", "-f", "string2.txt"};
		String[] f = {"int.txt", "float.txt", "string.txt", "string2.txt"};
		argsReader.read(a);
		
		Assert.assertEquals("my_job", argsReader.path);
		Assert.assertEquals("file_", argsReader.prefix);
		Assert.assertTrue(argsReader.addToExist);
		Assert.assertTrue(argsReader.showShortStatistic);
		Assert.assertTrue(argsReader.showFullStatistic);
		Assert.assertEquals(Arrays.asList(f), argsReader.files);
	}
	
	
}

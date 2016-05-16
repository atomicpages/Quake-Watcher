package edu.sdsu.watcher.quake.io;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileIOHelperTest {

	private static final String PATH = ".";
	private static final String[] DIRS = {"testDir1", "testDir2", "testDir3"};
	private static final String[] FILES = {"file1.txt", "file2.txt", "file3.txt"};
	private static Constructor<FileIOHelper> constructor;

	@BeforeClass
	public static void setup() throws NoSuchMethodException {
		constructor = FileIOHelper.class.getDeclaredConstructor();
		constructor.setAccessible(true);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testPrivateConstructor() throws InstantiationException, IllegalAccessException {
		assert Modifier.isPrivate(constructor.getModifiers());
		try {
			constructor.newInstance((Object[]) null);
		} catch(InvocationTargetException e) {
			if(e.getCause() instanceof UnsupportedOperationException) {
				throw new UnsupportedOperationException();
			}
		}
	}

	@Test
	public void testCreateDirNoExit() {
		FileIOHelper.createDirectory(PATH, DIRS[0], false, -1);
	}

	@Test
	public void testCreateDirExit() {
		FileIOHelper.createDirectory(PATH, DIRS[1], true, -1);
		assert exists(DIRS[1]);
	}

	@Test
	public void testCreateDirWithPathAndNameOnly() {
		FileIOHelper.createDirectory(PATH, DIRS[2]);
		assert exists(DIRS[2]);
	}

	@Test
	public void testDuplicateDirectory() {
		testCreateDirWithPathAndNameOnly();
	}

	@Test
	public void testWriteFile() {
		FileIOHelper.write(FILES[0], "Test");
		assert exists(FILES[0]);
	}

	@Test
	public void testExistingFile() throws IOException {
		testWriteFile();
		assert exists(FILES[0]);
		Files.deleteIfExists(Paths.get(FILES[0]));
	}

	@Test
	public void testEmptyArgsWrite() throws IOException {
		FileIOHelper.write(FILES[0], "");
		assert !exists(FILES[0]);
		Files.deleteIfExists(Paths.get(FILES[0]));
	}

	@Test
	public void testNullArgsWrite() throws IOException {
		FileIOHelper.write(FILES[1], null);
		assert !exists(FILES[1]);
		Files.deleteIfExists(Paths.get(FILES[0]));
	}

	@AfterClass
	public static void cleanup() throws IOException {
		for(String dir : DIRS) {
			Files.deleteIfExists(Paths.get(dir));
		}

		constructor.setAccessible(false);
	}

	/**
	 * @param name the file or directory to check
	 * @return true if the file or directory exists
	 */
	private static boolean exists(String name) {
		final Path path = Paths.get(PATH, name);
		return Files.exists(path);
	}

}

package edu.sdsu.watcher.quake.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Test;

public class FileIOHelperTest {

	@Test
	public void testCreateDirNoExit() {
		FileIOHelper.createDirectory("", "testDir", false, -1);
	}

	@Test
	public void testCreateDirExit() {
		FileIOHelper.createDirectory("", "testDir", true, -1);
	}

	@AfterClass
	public static void cleanup() throws IOException {
		Files.deleteIfExists(Paths.get("testDir"));
	}

}

package edu.sdsu.watcher.quake.cache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Test;

import edu.sdsu.watcher.quake.Shared;

public class CacheTest {

	private static Cache c1, c2, c3;

	static {
		c2 = new Cache("test", Shared.RESOURCES_PATH, false);
	}

	@Test
	public void testMissingCtorCreation() {
		c1 = new Cache("test", Shared.RESOURCES_PATH);
	}

	@Test
	public void testNullNameCtor() {
		c1 = new Cache(null, Shared.RESOURCES_PATH);
	}

	@Test
	public void testEmptyNameCtor() {
		c1 = new Cache("", Shared.RESOURCES_PATH);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyLocationCtor() {
		c1 = new Cache("test", "");
	}

	@Test(expected = NullPointerException.class)
	public void testNullExtension() {
		c3 = new Cache("test", Shared.RESOURCES_PATH, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyExtension() {
		c3 = new Cache("test", Shared.RESOURCES_PATH, "");
	}

	@Test
	public void testIsAppendTimestamp() {
		assert !c2.isAppendTimestamp();
	}

	@Test
	public void testGetName() {
		assert c2.getName().equals("test.json");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameEmptyString() {
		c1.setName("");
	}

	@Test
	public void testCreateCache() throws IOException {
		c2.create("{}");
	}

	@Test
	public void testDeleteCache() throws IOException {
		c2.delete();
	}

	@Test
	public void testLocation() {
		assert c2.getLocation().equals(Shared.RESOURCES_PATH);
	}

	@AfterClass
	public static void cleanup() throws IOException {
		final Path path = Paths.get(Shared.RESOURCES_PATH, "test.json");
		if(Files.exists(path)) {
			Files.delete(path);
		}
	}

}

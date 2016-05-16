package edu.sdsu.watcher.quake;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Test;

import com.google.common.io.Resources;

public class SimpleEarthquakeTest {

	private static SimpleEarthquake usgsLiveData = new SimpleEarthquake();
	private static SimpleEarthquake usgsCacheLiveData = new SimpleEarthquake(true, "cache_data", false);
//	private static SimpleEarthquake usgsCacheLiveDataAlt = new SimpleEarthquake(true, "cache_data");
	private static SimpleEarthquake usgsCachedData = new SimpleEarthquake(new File(Resources.getResource("all_hour.json").getFile()));

	@Test
	public void testValidTime() {
		usgsLiveData.getEarthquakes("significant", "hour");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidTime() {
		usgsLiveData.getEarthquakes("all", "year");
	}

	@Test
	public void testValidThreshold() {
		this.testValidTime();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidThreshold() {
		usgsLiveData.getEarthquakes("minor", "hour");
	}

	@Test
	public void testReadValidCache() {
		new SimpleEarthquake(new File(Resources.getResource("all_hour.json").getFile()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReadInvalidCache() {
		new SimpleEarthquake(new File(Resources.getResource("all.json").getFile()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBadCacheFileName() {
		new SimpleEarthquake(new File(Shared.BUILD_RESOURCES_PATH + "/bad.json"));
	}

	@Test
	public void testCacheDataLength() {
		assert usgsCachedData.getEarthquakes().size() == 2;
	}

	@Test
	public void testCacheDirCreation() {
		assert Files.exists(Paths.get("cache_data"));
	}

	@Test
	public void testCacheStorage() {
		usgsCacheLiveData.getEarthquakes("significant", "week");
		assert Files.exists(Paths.get("cache_data", "significant_week.json"));
	}

//	@Test
//	public void testCacheStorageAlt() {
//		usgsCacheLiveDataAlt.getEarthquakes("significant", "week");
//		findFiles(Paths.get(Shared.BUILD_RESOURCES_PATH), "^significant_week_\\d+");
//		assert Files.exists(Paths.get("cache_data", "significant_week.json"));
//	}

	@AfterClass
	public static void cleanup() throws IOException {
		final Path path = Paths.get("cache_data", "significant_week.json");
		final Path dataPath = Paths.get("cache_data");

		if(Files.exists(path)) {
			Files.delete(path);
		}

		if(Files.exists(dataPath)) {
			Files.delete(dataPath);
		}
	}

//	private static boolean findFiles(Path dir, final String regex) {
//		Preconditions.checkNotNull(dir, regex);
//		Preconditions.checkArgument(!regex.isEmpty());
//
//		List<String> files;
//
//		try {
////			files = Files.list(dir).filter(filter::matches).map(String::valueOf).collect(Collectors.toList());
//			files = Files.list(dir).map(String::valueOf).collect(Collectors.toList());
//			files.size();
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
//
//		return false;
//	}

}

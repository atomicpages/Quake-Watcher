package edu.sdsu.watcher.quake;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Test;

import com.google.common.io.Resources;

public class SimpleEarthquakeTest {

	private static SimpleEarthquake usgsLiveData = new SimpleEarthquake();
	private static SimpleEarthquake usgsCacheLiveData = new SimpleEarthquake(true, "cache_data");
	private static SimpleEarthquake usgsCachedData = new SimpleEarthquake(new File(Resources.getResource("all_hour.geojson").getFile()));

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
		new SimpleEarthquake(new File(Resources.getResource("all_hour.geojson").getFile()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReadInvalidCache() {
		new SimpleEarthquake(new File(Resources.getResource("all.geojson").getFile()));
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
		assert Files.exists(Paths.get("cache_data", "significant_week.geojson"));
	}

	@AfterClass
	public static void cleanup() throws IOException {
		Files.delete(Paths.get("cache_data", "significant_week.geojson"));
		Files.delete(Paths.get("cache_data"));
	}

}

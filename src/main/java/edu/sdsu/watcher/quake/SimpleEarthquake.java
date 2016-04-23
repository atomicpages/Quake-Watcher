package edu.sdsu.watcher.quake;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import edu.sdsu.watcher.quake.factories.DecodeJson;
import edu.sdsu.watcher.quake.io.FileIOHelper;
import edu.sdsu.watcher.quake.net.JsonReader;
import edu.sdsu.watcher.quake.net.Reader;
import edu.sdsu.watcher.quake.structures.QuakeStruct;

/**
 * Handles the details of getting the data from the remote or local resource,
 * filtering the data, and returning the user with usable data.
 * @author Dennis Thompson
 */
public class SimpleEarthquake {

	private static final Reader jsonReader = JsonReader.getInstance();
	private static QuakeStruct struct = null;

	private static final String USGS_URL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/";

	private static HashSet<String> time, threshold;

	static {
		time = new HashSet<>();
		threshold = new HashSet<>();

		time.add("hour");
		time.add("day");
		time.add("week");
		time.add("month");

		threshold.add("significant");
		threshold.add("all");
		threshold.add("4.5");
		threshold.add("2.5");
		threshold.add("1.0");
	}

	private boolean online;
	private boolean cacheJson;
	private File cache;
	private Path cachePath;

	/**
	 * Reads live data from the USGS website.
	 */
	public SimpleEarthquake() {
		this.online = true;
	}

	/**
	 * <p>Construct a new Object with caching ability. If you don't need the ability to cache then usage of this
	 * constructor is discouraged &ndash; use {@link SimpleEarthquake#getEarthquakes()} for live data or use</p>
	 * <p>Note: This constructor will first gather the live data once {@link SimpleEarthquake#getEarthquakes(String, String)}
	 * is called and then save the {@code json} data in the location specified. Sample usage:</p>
	 * <pre>
	 *     SimpleEarthquake test = new SimpleEarthquake(true, "/home/foo/Desktop");
	 *     List&lt;Earthquake&gt; quakes = test.getEarthquakes("all", "hour");
	 * </pre>
	 * {@link SimpleEarthquake#SimpleEarthquake(File)} to read from an existing cache.
	 * @param cache Set true to cache the downloaded {@code json} file.
	 * @param location The path of the file without the file (this will be created automatically).
	 */
	public SimpleEarthquake(final boolean cache, final String location) {
		this();
		this.cacheJson = cache;

		if(this.cacheJson) {
			if(location.matches(".*[^\\.\\w+]$")) {
				Path path = Paths.get(location);
				if(!Files.exists(path)) {
					FileIOHelper.createDirectory(path);
				}
				this.cachePath = path;
			} else { // prepare string
				// saves last little bit of the path
				final String temp = location.substring(location.lastIndexOf(System.getProperty("file.separator")) + 1);
				if(!temp.matches("\\w+\\.\\w+")) { // does not have a file structure
					final Path path = Paths.get(com.google.common.io.Files.simplifyPath(location));
					FileIOHelper.createDirectory(path);
					this.cachePath = path;
				} else {
					final String[] segments = location.split(System.getProperty("file.separator"));
					final Path path = Paths.get(segments[segments.length - 2]);
					FileIOHelper.createDirectory(path);
					this.cachePath = path;
				}
			}
		}
	}

	/**
	 * Reads a local file and uses cached data.
	 * @param file the cached {@code json} file to read from.
	 */
	public SimpleEarthquake(final File file) {
		Preconditions.checkNotNull(file);
		Preconditions.checkArgument(file.exists() && file.canRead());
		this.online = false;
		this.cache = file;
	}

	/**
	 * <p>Gets a list of earthquakes found within the given threshold and time.</p>
	 * <p>Acceptable units for threshold are:</p>
	 * <ul>
	 *     <li>significant</li>
	 *     <li>all</li>
	 *     <li>4.5</li>
	 *     <li>2.5</li>
	 *     <li>1.0</li>
	 * </ul>
	 * <p>Acceptable units for time are:</p>
	 * <ul>
	 *     <li>month</li>
	 *     <li>week</li>
	 *     <li>day</li>
	 *     <li>hour</li>
	 * </ul>
	 * @param threshold the threshold to filter by.
	 * @param time the time to filer by.
	 * @return a list of earthquakes or an empty list if none are found.
	 */
	public List<Earthquake> getEarthquakes(String threshold, String time) {
		threshold = threshold.toLowerCase();
		time = time.toLowerCase();

		if(!isValidThreshold(threshold)) {
			throw new IllegalArgumentException(
					buildErrors("Invalid threshold. Must be one of: ", SimpleEarthquake.threshold)
			);
		}

		if(!isValidTime(time)) {
			throw new IllegalArgumentException(
					buildErrors("Invalid time. Must be one of: ", SimpleEarthquake.time)
			);
		}

		try {
			if(this.online) {
				final String url = String.format("%s%s_%s.geojson", USGS_URL, threshold, time);
				final String json = jsonReader.get(url);
				if(this.cacheJson) {
					final String cacheFilePath = this.cachePath.toString() + System.getProperty("file.separator");
					final String cacheFileName = String.format("%s_%s.geojson", threshold, time);
					final String cacheFileNamePretty = String.format("pretty_%s_%s.geojson", threshold, time);

					FileIOHelper.write(cacheFilePath + cacheFileName, json);

					// TODO: allow user to configure this
//					FileIOHelper.write(cacheFilePath + cacheFileNamePretty, prettyPrintJson(json));
				}
				struct = DecodeJson.parse(json, QuakeStruct.class);
			} else {
				struct = DecodeJson.parse(this.cache, QuakeStruct.class);
			}
		} catch(IOException e) {
			System.err.println("A parse error occurred! Check the stack trace for more details.");
			e.printStackTrace();
			System.exit(ExitCodes.GSON_PARSE_ERROR);
		}

		return processFilter(struct.getFeatures());
	}

	/**
	 * <p>Gets a list of <strong>all</strong> earthquakes found within a week time frame.</p>
	 * @return a list of earthquakes or an empty list if none are found.
	 * @see SimpleEarthquake#getEarthquakes(String, String)
	 */
	public List<Earthquake> getEarthquakes() {
		return this.getEarthquakes("all", "week");
	}

	private static boolean isValidThreshold(final String threshold) {
		return SimpleEarthquake.threshold.contains(threshold);
	}

	/**
	 * @param time The time interval to validate.
	 * @return True is the time unit is valid.
	 */
	private static boolean isValidTime(final String time) {
		return SimpleEarthquake.time.contains(time);
	}

	/**
	 * Handles the creation of the custom error messages when the time or threshold intervals
	 * are incorrect.
	 * @param prepend Prepends the buffer with a custom message.
	 * @param set The set to combine into a comma-delimited list.
	 * @return A comma-delimited list with a custom prepended message.
	 */
	private static String buildErrors(final String prepend, final Set<String> set) {
		String buffer = prepend;
		buffer += set.stream().collect(Collectors.joining(", "));
		return buffer;
	}

	/**
	 * Filters the list using the default filter.
	 * @param list The list to filter.
	 * @return A list of filtered Earthquake objects.
	 */
	private static List<Earthquake> processFilter(List<QuakeStruct.Features> list) {
		// TODO: add support for custom filters
		Preconditions.checkNotNull(list);
		final List<Earthquake> quakes = new ArrayList<>();

		if(!list.isEmpty()) {
			list.forEach(feature -> quakes.add(filter(feature)));
		}

		return quakes;
	}

	/**
	 * Defines the filter rules for the Earthquake object.
	 * @param features The features list to filter.
	 * @return an Earthquake object containing all of the items needed from the features list.
	 */
	private static Earthquake filter(QuakeStruct.Features features) {
		// variables spelled out for illustrative purposes
		final double lon = features.getGeometry().getCoordinates().get(0);
		final double lat = features.getGeometry().getCoordinates().get(1);
		final double depth = features.getGeometry().getCoordinates().get(2);

		final double magnitude = features.getProperties().getMag();
		final String description = features.getProperties().getTitle();
		final long time = features.getProperties().getTime();

		final String id = features.getId();

		return new Earthquake(new Coordinate(lon, lat, depth), magnitude, description, id, time);
	}

	/**
	 * @param uglyJson the ugly {@code json} string to beautify.
	 * @return the beautified {@code json} string.
	 */
	private static String prettyPrintJson(String uglyJson) {
		Preconditions.checkNotNull(uglyJson);
		Preconditions.checkArgument(!uglyJson.isEmpty());

		final Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(new JsonParser().parse(uglyJson));
	}

}
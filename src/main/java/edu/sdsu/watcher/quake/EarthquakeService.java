package edu.sdsu.watcher.quake;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import edu.sdsu.watcher.quake.factories.DecodeJson;
import edu.sdsu.watcher.quake.net.JsonReader;
import edu.sdsu.watcher.quake.net.Reader;
import edu.sdsu.watcher.quake.structures.QuakeStruct;

/**
 * Handles the details of getting the data from the remote or local resource,
 * filtering the data, and returning the user with usable data.
 * @author Dennis Thompson
 */
public class EarthquakeService {

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

	public EarthquakeService() {
		this.online = true;
	}

	// TODO: finish implementing reading from a cached json file
	public EarthquakeService(File file) {
		Preconditions.checkNotNull(file);
		Preconditions.checkArgument(file.exists() && file.canRead());
		this.online = false;
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
					buildErrors("Invalid threshold. Must be one of: ", EarthquakeService.threshold)
			);
		}

		if(!isValidTime(time)) {
			throw new IllegalArgumentException(
					buildErrors("Invalid time. Must be one of: ", EarthquakeService.time)
			);
		}

		final String url = String.format("%s%s_%s.geojson", USGS_URL, threshold, time);
		try {
			struct = DecodeJson.parse(jsonReader.get(url), QuakeStruct.class);
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
	 * @see EarthquakeService#getEarthquakes(String, String)
	 */
	public List<Earthquake> getEarthquakes() {
		return this.getEarthquakes("all", "week");
	}

	private static boolean isValidThreshold(final String threshold) {
		return EarthquakeService.threshold.contains(threshold);
	}

	private static boolean isValidTime(final String time) {
		return EarthquakeService.time.contains(time);
	}

	private static String buildErrors(final String prepend, final Set<String> set) {
		String buffer = prepend;
		buffer += set.stream().collect(Collectors.joining(", "));
		return buffer;
	}

	private static List<Earthquake> processFilter(List<QuakeStruct.Features> list) {
		Preconditions.checkNotNull(list);
		final List<Earthquake> quakes = new ArrayList<>();

		if(!list.isEmpty()) {
			list.forEach(feature -> quakes.add(filter(feature)));
		}

		return quakes;
	}

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

}

package edu.sdsu.watcher.quake.cache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.base.Preconditions;
import edu.sdsu.watcher.quake.io.FileIOHelper;

/**
 * <p>A simple Cache class helper that aides in creating and updating existing cache files.
 * This class does not:</p>
 * <ul>
 *      <li>Set expiration times on caches</li>
 *      <li>Invalidate expired caches automatically</li>
 *      <li>Store caches outside of the local file system</li>
 *      <li>Store caches in a dictionary, database, map, or otherwise a general Object</li>
 * </ul>
 * <p>This class does:</p>
 * <ul>
 *      <li>Create files at a specified location</li>
 *      <li>Adds a timestamp to the name of the file (can be configured)</li>
 *      <li>Provide uses with the ability to run CRUD operations on existing caches</li>
 * </ul>
 */
public class Cache {

	private static final long TIME = System.currentTimeMillis();
	private static final String DEFAULT_FILE_EXT = ".json";

	private String name, location, extension;
	private boolean appendTimestamp;
	private Path path;

	/**
	 * @param fileName The name of the cache file.
	 * @param location The location to store the cache file.
	 */
	public Cache(String fileName, String location) {
		this(fileName, location, DEFAULT_FILE_EXT);
	}

	/**
	 * @param fileName        The name of the cache file.
	 * @param location        The location to store the cache file.
	 * @param appendTimestamp Set true to append the timestamp to the cache file name.
	 */
	public Cache(String fileName, String location, boolean appendTimestamp) {
		this(fileName, location, DEFAULT_FILE_EXT, appendTimestamp);
	}

	/**
	 * @param fileName  The name of the cache file.
	 * @param location  The location to store the cache file.
	 * @param extension The extension to give the cache file.
	 */
	public Cache(String fileName, String location, String extension) {
		this(fileName, location, extension, true);
	}

	/**
	 * @param fileName        The name of the cache file.
	 * @param location        The location to store the cache file.
	 * @param extension       The extension to give the cache file.
	 * @param appendTimestamp Set true to append the timestamp to the cache file name.
	 */
	public Cache(String fileName, String location, String extension, boolean appendTimestamp) {
		Preconditions.checkNotNull(location, extension);
		Preconditions.checkArgument(!location.isEmpty());
		Preconditions.checkArgument(location.matches(".*[^\\.\\w+]$"), "Malformed cache location. Missing trailing slash."); // make no attempt to fix the location
		Preconditions.checkArgument(extension.matches("\\.\\w+"));

		this.extension = extension;
		this.appendTimestamp = appendTimestamp;
		this.name = fileName;

		if(this.name != null && !this.name.isEmpty()) {
			if(this.appendTimestamp) {
				this.name += TIME;
			}
			this.name += normalizeExtension(extension);
		}

		this.location = location;
		this.initialize();
	}

	/**
	 * @return True if we're appending the timestamp to the name of the cache file.
	 */
	public boolean isAppendTimestamp() {
		return this.appendTimestamp;
	}

	/**
	 * @return The name of the cache file.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * <p>Overrides the name of the cache <em>only when the name supplied to the constructor
	 * is {@code null} or an empty string.</em> This is a basic defense mechanism
	 * that prevents the name of the file being changed during runtime.</p>
	 *
	 * @param name The name of the cache file.
	 */
	public void setName(final String name) {
		Preconditions.checkNotNull(name);
		Preconditions.checkArgument(!name.isEmpty());

		if(this.name == null || this.name.isEmpty()) {
			this.name = name;
			if(this.appendTimestamp) this.name += TIME;
			this.name += this.extension;
			this.path = Paths.get(this.location, this.name);
		}
	}

	/**
	 * Appends data to the cache file.
	 * @param content The message to append to the cache file.
	 * @throws IOException Thrown upon error when writing to the file.
	 */
	public void append(String content) throws IOException {
		Preconditions.checkNotNull(this.path);
		CacheWriter.append(this.path, content);
	}

	/**
	 * Truncates an existing cache file or creates one if it does not exist.
	 * @param content The message to append to the cache file.
	 * @throws IOException Thrown upon error when writing to the file.
	 */
	public void create(String content) throws IOException {
		Preconditions.checkNotNull(this.path);
		CacheWriter.create(this.path, content);
	}

	/**
	 * @return True if the operation was successful.
	 */
	public boolean delete() {
		Preconditions.checkNotNull(this.path);
		return CacheWriter.delete(this.path);
	}

	/**
	 * @return The location to the cache file.
	 */
	public String getLocation() {
		return this.location;
	}

	private void initialize() {
		Path path = null;

		if(this.name != null && !this.name.isEmpty()) {
			this.path = path = Paths.get(this.location, this.name);
		} else {
			path = Paths.get(this.location);
		}

		if(!Files.exists(path)) {
			FileIOHelper.createDirectory(path);
		}
	}

	private static String normalizeExtension(String ext) {
		Preconditions.checkNotNull(ext);
		Preconditions.checkArgument(!ext.isEmpty() && ext.matches("^\\.?\\w+"));

		if(ext.charAt(0) != '.') {
			ext = "." + ext;
		}

		return ext;
	}

}

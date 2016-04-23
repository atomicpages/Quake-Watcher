package edu.sdsu.watcher.quake.cache;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.base.Preconditions;
import edu.sdsu.watcher.quake.io.FileIOHelper;

/**
 * <p>A simple Cache class helper that aides in creating and updating existing cache files.
 * This class does not:</p>
 * <ul>
 *     <li>Set expiration times on caches</li>
 *     <li>Invalidate expired caches</li>
 *     <li>Store caches outside of the local file system</li>
 *     <li>Store caches in a dictionary, database, map, or otherwise a general Object</li>
 * </ul>
 * <p>This class does:</p>
 * <ul>
 *     <li>Create files at a specified location</li>
 *     <li>Adds a timestamp to the name of the file (can be configured)</li>
 * </ul>
 */
public class Cache {

	private static final long TIME = System.currentTimeMillis();
	private static final String DEFAULT_FILE_EXT = ".json";

	private String name, path;
	private boolean appendTimestamp;

	/**
	 * @param cacheFileName The name of the cache file.
	 * @param cachePath The path to store the cache file.
	 */
	public Cache(String cacheFileName, String cachePath) {
		this(cacheFileName, cachePath, DEFAULT_FILE_EXT);
	}

	/**
	 * @param cacheFileName The name of the cache file.
	 * @param cachePath The path to store the cache file.
	 * @param appendTimestamp Set true to append the timestamp to the cache file name.
	 */
	public Cache(String cacheFileName, String cachePath, boolean appendTimestamp) {
		this(cacheFileName, cachePath, DEFAULT_FILE_EXT, appendTimestamp);
	}

	/**
	 * @param cacheFileName The name of the cache file.
	 * @param cachePath The path to store the cache file.
	 * @param extension The extension to give the cache file.
	 */
	public Cache(String cacheFileName, String cachePath, String extension) {
		this(cacheFileName, cachePath, extension, true);
	}

	/**
	 * @param cacheFileName The name of the cache file.
	 * @param cachePath The path to store the cache file.
	 * @param extension The extension to give the cache file.
	 * @param appendTimestamp Set true to append the timestamp to the cache file name.
	 */
	public Cache(String cacheFileName, String cachePath, String extension, boolean appendTimestamp) {
		Preconditions.checkNotNull(cacheFileName, cachePath, extension);
		Preconditions.checkArgument(!cacheFileName.isEmpty(), !cachePath.isEmpty());
		Preconditions.checkArgument(cachePath.matches(".*[^\\.\\w+]$")); // make no attempt to fix the path

		this.appendTimestamp = appendTimestamp;
		this.name = cacheFileName;
		if(this.appendTimestamp) this.name += TIME;
		this.name += normalizeExtension(extension);
		this.path = cachePath;
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
	 * @return The path to the cache file.
	 */
	public String getPath() {
		return this.path;
	}

	private static String normalizeExtension(String ext) {
		Preconditions.checkNotNull(ext);
		Preconditions.checkArgument(!ext.isEmpty() && ext.matches("^\\.?\\w+"));

		if(ext.charAt(0) != '.') {
			ext = "." + ext;
		}

		return ext;
	}

	private void initialize() {
		final Path path = Paths.get((this.path));

		if(!Files.exists(path)) {
			FileIOHelper.createDirectory(path);
		}
	}

}

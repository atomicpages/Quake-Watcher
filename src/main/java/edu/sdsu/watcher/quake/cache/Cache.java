package edu.sdsu.watcher.quake.cache;

import com.google.common.base.Preconditions;

public class Cache {

	private static final long TIME = System.currentTimeMillis();
	private static final String DEFAULT_FILE_EXT = ".json";

	private String name, path;

	public Cache(String cacheFileName, String cachePath) {
		this(cacheFileName, cachePath, DEFAULT_FILE_EXT);
	}

	public Cache(String cacheFileName, String cachePath, String extension) {
		this.name = cacheFileName + TIME + normalizeExtension(extension);
		this.path = cachePath;
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

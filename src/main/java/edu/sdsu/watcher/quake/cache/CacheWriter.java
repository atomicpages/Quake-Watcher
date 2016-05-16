package edu.sdsu.watcher.quake.cache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * A very simple File IO helper specifically for the {@link Cache} class. Utilizing the NIO
 * libraries, we are able to do CRUD operations on cache files easily.
 *
 * @author Dennis Thompson
 */
final class CacheWriter {

	private CacheWriter() {}

	/**
	 * Appends information to the cache file.
	 * @param path The full path with file to work with.
	 * @param content The content to append to the file.
	 * @throws IOException Thrown upon error when writing to the file.
	 */
	static void append(Path path, String content) throws IOException {
		cru(path, StandardOpenOption.APPEND, content);
	}

	/**
	 * Truncates an existing file if it exists, otherwise creates a new file.
	 * @param path The full path with file to work with.
	 * @param content The content to append to the file.
	 * @throws IOException Thrown upon error when writing to the file.
	 */
	static void create(Path path, String content) throws IOException {
		cru(path, StandardOpenOption.TRUNCATE_EXISTING, content);
	}

	/**
	 * @param path The full path with file to remove.
	 * @return True if the file exists and it was removed.
	 */
	static boolean delete(Path path) {
			return Files.exists(path) && path.toFile().delete();
	}

	/**
	 * Handles the create, read, and update logic &ndash; hence "cru".
	 * @param path The full path with file to work with.
	 * @param mode The mode to work with.
	 * @param content The content to append to the file.
	 * @throws IOException Thrown upon error when writing to the file.
	 */
	private static void cru(Path path, StandardOpenOption mode, String content) throws IOException {
		if(path != null && content != null && !content.isEmpty()) {
			StandardOpenOption tempMode = mode;
			if(!Files.exists(path)) {
				tempMode = StandardOpenOption.CREATE_NEW;
			}

			Files.write(path, content.getBytes(), tempMode);
		}
	}

}

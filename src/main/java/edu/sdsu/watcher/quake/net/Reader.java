package edu.sdsu.watcher.quake.net;

import java.io.IOException;

/**
 * Creates the scaffolding for any future Readers.
 * @author Dennis Thompson
 */
public interface Reader {

	/**
	 * Gets a remote resource and returns a String of said resource.
	 * @param source The remote resource to pull from.
	 * @return The resource as a String.
	 * @throws IOException is there is an IO error.
	 */
	String get(String source) throws IOException;

}

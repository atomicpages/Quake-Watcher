package edu.sdsu.watcher.quake.net;

/**
 * Creates the scaffolding for any future Readers.
 * @author Dennis Thompson
 */
public interface Reader {

	/**
	 * Gets a remote resource and returns a String of said resource.
	 * @param source The remote resource to pull from.
	 * @return The resource as a String.
	 */
	String get(String source);

}

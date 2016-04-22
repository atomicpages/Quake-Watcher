package edu.sdsu.watcher.quake;

/**
 * Contains all of the exit codes used for this project.
 * @author Dennis Thompson
 */
public interface ExitCodes {

	/**
	 * A general network error occurred.
	 */
	int NET_READ_ERROR = 200;

	/**
	 * There was a general error when Gson was processing the {@code json} String.
	 */
	int GSON_PARSE_ERROR = 201;

}

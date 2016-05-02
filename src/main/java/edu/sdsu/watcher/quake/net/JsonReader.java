package edu.sdsu.watcher.quake.net;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * A singleton that grabs a remote {@code json} String and stores the
 * result as a String so it can be passed on for additional processing.
 * @author Dennis Thompson
 */
public final class JsonReader implements Reader {

	private static class InstanceHolder {
		private static final Reader instance = new JsonReader();
	}

	private JsonReader() {}

	/**
	 * @return the instance of JsonReader.
	 */
	public static Reader getInstance() {
		return InstanceHolder.instance;
	}

	/**
	 * Gets a remote resource and returns a String of said resource.
	 * @param url The URL to read from.
	 * @return A String representing what was read from the URL.
	 * @see OkHttpClient
	 */
	public String get(final String url) throws IOException {
		checkArgs(url);
		final OkHttpClient client = new OkHttpClient();

		final Request request = new Request.Builder().url(url).build();
		return client.newCall(request).execute().body().string();
	}

	/**
	 * Ensures data is valid before we attempt to process it.
	 * @param args the vargs data to check
	 */
	private static void checkArgs(final String... args) {
		if(args.length == 0) {
			throw new IllegalArgumentException("Number of parameters in JsonReader.checkArgs cannot be empty!");
		}

		for(String arg : args) {
			if(arg == null || arg.length() == 0) {
				throw new IllegalArgumentException(arg + " cannot be null or empty!");
			}
		}
	}

}

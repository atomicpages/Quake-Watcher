package edu.sdsu.watcher.quake.factories;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;

/**
 * <p>A very simple class that aides in decoding {@code json} via {@link Gson} in a generic manner.</p>
 * <p>Sample usage:</p>
 * <pre>
 * final QuakeStruct struct = DecodeJson.parse("...", QuakeStruct.class);
 * </pre>
 * @author Dennis Thompson
 */
public final class DecodeJson {

	private DecodeJson() {}

	private static Gson GSON = new Gson();

	/**
	 *
	 * @param file the file to process that contains the {@code json} string.
	 * @param clazz the class to apply the {@code json} to.
	 * @param <E> the generic type of the class.
	 * @return an object representation of the {@code json} data.
	 * @throws IOException if Gson encounters an error.
	 */
	public static <E> E parse(final File file, Class<E> clazz) throws IOException {
		Preconditions.checkNotNull(file);
		Preconditions.checkArgument(file.exists() && file.canRead());
		return GSON.fromJson(new FileReader(file), clazz);
	}

	/**
	 *
	 * @param content the {@code json} string to parse.
	 * @param clazz the class to apply the {@code json} to.
	 * @param <E> the generic type of the class.
	 * @return an object representation of the {@code json} data.
	 * @throws IOException if Gson encounters an error.
	 */
	public static <E> E parse(final String content, Class<E> clazz) throws IOException {
		Preconditions.checkNotNull(content, clazz);
		Preconditions.checkArgument(content.length() != 0);
		return GSON.fromJson(content, clazz);
	}

}

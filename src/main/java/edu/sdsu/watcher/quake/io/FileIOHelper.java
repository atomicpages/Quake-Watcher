package edu.sdsu.watcher.quake.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.google.common.base.Preconditions;

/**
 * <p>A simple static helper class that aides in common I/O operations such as:</p>
 * <ol>
 *     <li>Directory creation</li>
 *     <li>File creation</li>
 *     <li>File writing</li>
 *     <li>File deletion</li>
 * </ol>
 * <p>This class utilizes the Java non-blocking I/O {@code java.nio} libraries to
 * achieve better, thread-safe I/O operations.</p>
 */
public final class FileIOHelper {

	/** Private constructor so we don't instantiate the class &mdash; it's static. */
	private FileIOHelper() {}

	/**
	 * Creates a directory on the file system.
	 * @param path    the path to create the directory
	 * @param dirName the name of the directory to create
	 */
	public static void createDirectory(final String path, final String dirName) {
		createDirectory(path, dirName, false, -1);
	}

	/**
	 * Creates a directory on the file system.
	 * @param path          the path to create the directory
	 * @param dirName       the name of the directory to create
	 * @param exitOnFailure set true to exit on {@link IOException}
	 * @param code          the exit code to exit with
	 */
	public static void createDirectory(String path, final String dirName, final boolean exitOnFailure, final int code) {
		if(checkArgs(path, dirName)) {
			final String tempDirPath = path + System.getProperty("file.separator") + dirName;
			try {
				if(Files.notExists(Paths.get(tempDirPath))) {
					Files.createDirectory(Paths.get(tempDirPath));
				}
			} catch(IOException e) {
				handleException(e, "Problem creating " + tempDirPath);
				if(exitOnFailure) {
					System.exit(code);
				}
			}
		}
	}

	/**
	 * Creates a directory on the file system.
	 * @param path the predefined Path object to work with.
	 */
	public static void createDirectory(final Path path) {
		Preconditions.checkNotNull(path);

		try {
			if(Files.notExists(path)) {
				Files.createDirectory(path);
			}
		} catch(IOException e) {
			handleException(e, "Problem creating " + path.toString());
		}
	}

	/**
	 * Deletes the file IFF it exists.
	 * @param path the path to find the file
	 * @param file the file to find
	 */
	public static void deleteFile(final String path, final String file) {
		if(checkArgs(path, file)) {
			try {
				Files.deleteIfExists(Paths.get(path, file));
			} catch(IOException e) {
				handleException(e, "Problem deleting " + file, false);
			}
		}
	}

	/**
	 * Write the contents to a file.
	 * @param file    the path and name of the file
	 * @param message the message to add to the file
	 */
	public static void write(final String file, final String message) {
		write(file, message, true);
	}

	/**
	 * Write the contents to a file.
	 * @param file          the path and name of the file
	 * @param message       the message to add to the file
	 * @param appendNewLine set true to add a new line to the end of the message
	 */
	public static void write(final String file, String message, final boolean appendNewLine) {
		if(checkArgs(file, message)) {
			final Path path = Paths.get(file);
			StandardOpenOption mode = StandardOpenOption.APPEND;
			if(Files.notExists(path)) {
				mode = StandardOpenOption.CREATE_NEW;
			}
			if(appendNewLine) {
				message += System.getProperty("line.separator");
			}
			try {
				Files.write(path, message.getBytes(), mode);
			} catch(IOException e) {
				handleException(e, "Problem writing to " + file);
			}
		}
	}

	/**
	 * <p>Validates the arguments by ensuring the parameters are:</p>
	 * <ol>
	 *      <li>Not null</li>
	 *      <li>Not empty</li>
	 * </ol>
	 * @param args variable args to check, cannot be {@code null} or empty
	 * @return true if the arguments are valid.
	 */
	private static boolean checkArgs(final String... args) {
		if(args != null && args.length > 0) {
			for(final String arg : args) {
				if(arg == null || arg.isEmpty()) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Decides what happens by default in the catch blocks.
	 * @param e         the {@code IOException} instance to use
	 * @param errorMsg  he custom error message to print to stderr
	 */
	private static void handleException(final IOException e, final String errorMsg) {
		handleException(e, errorMsg, true);
	}

	/**
	 * Decides what happens by default in the catch blocks.
	 * @param e             the {@code IOException} instance to use
	 * @param errorMsg      the custom error message to print to stderr
	 * @param printStace    set true to print the stack trace
	 */
	private static void handleException(final IOException e, final String errorMsg, final boolean printStace) {
		checkArgs(errorMsg);

		System.err.println(errorMsg);
		System.err.println(e.getMessage());

		if(printStace) {
			e.printStackTrace();
		}
	}

}

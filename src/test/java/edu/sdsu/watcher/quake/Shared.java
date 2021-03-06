package edu.sdsu.watcher.quake;

public class Shared {

	private static final String RAW_PATH = Shared.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	public static final String BUILD_RESOURCES_PATH = repairPath(RAW_PATH, RAW_PATH.indexOf("classes"));
	public static String RESOURCES_PATH = Shared.class.getClassLoader().getResource("all_hour.json").getPath();
	public static final String FS = System.getProperty("file.separator");

	static {
		int index = RESOURCES_PATH.lastIndexOf("test/");
		int start = 0;
		if(System.getProperty("os.name").contains("Windows")) {
			start = 3;
		}

		RESOURCES_PATH = RESOURCES_PATH.substring(start, index) + "test/";
	}

	private static String repairPath(String path, int index) {
		return path.substring(0, index) + "resources" + FS + "test" + FS;
	}

}

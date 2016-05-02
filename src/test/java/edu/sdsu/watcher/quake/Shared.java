package edu.sdsu.watcher.quake;

class Shared {

	private static final String RAW_PATH = Shared.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	public static final String BUILD_RESOURCES_PATH = repairPath(RAW_PATH, RAW_PATH.indexOf("classes"));
//	public static final String SAVE_RESOURCES_PATH = Shared.class.getClassLoader().getResources("all_hour.json");

	private static String repairPath(String path, int index) {
		return path.substring(0, index) + "resources/test/";
	}

}

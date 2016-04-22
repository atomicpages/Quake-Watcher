package edu.sdsu.watcher.quake;

/**
 * The longitudinal, latitudinal, and depth where the earthquake occurred.
 * @author Dennis Thompson
 */
public class Coordinate {

	private double longitude, latitude, depth;

	public Coordinate(double longitude, double latitude, double depth) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.depth = depth;
	}

	/**
	 * @return The longitude (West-North) component.
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @return The latitude (South-North) component.
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Gets the <strong>hypocenter</strong> of the quake.
	 * @return The depth (closer or farther from the surface) component.
	 */
	public double getDepth() {
		return depth;
	}

	@Override
	public String toString() {
		return "Coordinate[" +
				"longitude=" + longitude +
				", latitude=" + latitude +
				", depth=" + depth +
				']';
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || this.getClass() != o.getClass()) return false;

		Coordinate that = (Coordinate) o;

		if(Double.compare(that.longitude, longitude) != 0) return false;
		if(Double.compare(that.latitude, latitude) != 0) return false;
		return Double.compare(that.depth, depth) == 0;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		temp = Double.doubleToLongBits(longitude);
		result = (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(latitude);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(depth);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}

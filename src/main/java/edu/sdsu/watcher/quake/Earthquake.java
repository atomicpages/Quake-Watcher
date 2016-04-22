package edu.sdsu.watcher.quake;

import java.util.Date;

/**
 * Houses all of the information pertaining to any quake.
 * @author Dennis Thompson
 */
public class Earthquake {

	private Coordinate coordinate;
	private double magnitude;
	private String description, id;
	private long time;

	public Earthquake(final Coordinate coordinate, final double magnitude,
	                  final String description, final String id, final long time) {
		this.coordinate = coordinate;
		this.magnitude = magnitude;
		this.description = description;
		this.id = id;
		this.time = time;
	}

	/**
	 * Gets the coordinate object which contains the latitude and longitude of the quake.
	 * @return The coordinate object.
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}

	/**
	 * @return The magnitude of the earthquake on the Richter Scale.
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * @return A human-readable description of the location.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return A uniquely identifying id for this earthquake.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return The epoch time when this earthquake occurred.
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Gets the clean version of the date as a String (e.g. Tue Apr 19 03:46:53 GMT 2016).
	 * @return A clean version of the date.
	 * @see java.text.DateFormat
	 */
	public String getNeatDate() {
		return new Date(time).toString();
	}

	@Override
	public String toString() {
		return "Earthquake[" +
				"coordinate=" + coordinate +
				", magnitude=" + magnitude +
				", description='" + description + '\'' +
				", id='" + id + '\'' +
				", time=" + time +
				']';
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		Earthquake that = (Earthquake) o;

		if(Double.compare(that.magnitude, magnitude) != 0) return false;
		if(time != that.time) return false;
		if(!coordinate.equals(that.coordinate)) return false;
		if(!description.equals(that.description)) return false;
		return id.equals(that.id);

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = coordinate.hashCode();
		temp = Double.doubleToLongBits(magnitude);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + description.hashCode();
		result = 31 * result + id.hashCode();
		result = 31 * result + (int) (time ^ (time >>> 32));
		return result;
	}
}

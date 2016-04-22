package edu.sdsu.watcher.quake;

import org.junit.Test;

public class CoordinateTest {

	static Coordinate c1, c2, c3;

	// TODO: populate with random data
	static {
		c1 = new Coordinate(-79.9398, 0.3715, 19.16);
		c2 = new Coordinate(-79.9398, 0.3715, 19.16);
		c3 = new Coordinate(79.9398, 0.3715, 19.16);
	}

	@Test
	public void testEquals() {
		assert c1.equals(c2);
	}

	@Test
	public void testInequality() {
		assert !c1.equals(c3);
	}

	// ensure no data is randomly transformed
	@Test
	public void testGetLatitude() {
		assert c1.getLatitude() == 0.3715;
	}

	@Test
	public void testGetLongitude() {
		assert c1.getLongitude() == -79.9398;
	}

	@Test
	public void testGetDepth() {
		assert c1.getDepth() == 19.16;
	}

	@Test
	public void testHashCode() {
		assert c1.hashCode() == c2.hashCode();
	}

	@Test
	public void testHashCodeFailing() {
		assert c1.hashCode() != c3.hashCode();
	}

	@Test
	public void testToStringFormat() {
		final String pattern = "^Coordinate\\[longitude=\\-?\\d+\\.\\d{2,},\\slatitude=\\d+\\.\\d{2,},\\sdepth=\\d+\\.?\\d*\\]$";
		c1.toString().matches(pattern);
	}

	@Test
	public void testToString() {
		assert c1.toString().equals("Coordinate[longitude=-79.9398, latitude=0.3715, depth=19.16]");
	}

}
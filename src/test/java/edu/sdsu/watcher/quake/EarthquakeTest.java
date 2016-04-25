package edu.sdsu.watcher.quake;

import org.junit.Test;

public class EarthquakeTest {

	static Earthquake e1, e2, e3;

	// TODO: populate with random data
	static {
		e1 = new Earthquake(CoordinateTest.c1, 7.8, "M 7.8 - 27km SSE of Muisne, Ecuador", "us20005j32", 1460851117280L);
		e2 = new Earthquake(CoordinateTest.c2, 7.8, "M 7.8 - 27km SSE of Muisne, Ecuador", "us20005j32", 1460851117280L);
		e3 = new Earthquake(CoordinateTest.c1, 6.8, "M 7.8 - 27km SSE of Muisne, Ecuador", "us20005j32", 1460851117280L);
	}

	@Test
	public void testEquals() {
		assert e1.equals(e2);
	}

	@Test
	public void testInequality() {
		assert !e1.equals(e3);
	}

	@Test
	public void testGetCoordinate() {
		assert e1.getCoordinate().equals(CoordinateTest.c1)
				&& e1.getCoordinate() == CoordinateTest.c1;
	}

	@Test
	public void testFailingGetCoordinate() {
		assert e1.getCoordinate().equals(CoordinateTest.c1)
				&& e1.getCoordinate() != CoordinateTest.c2;
	}

	@Test
	public void testGetId() {
		assert e1.getId().equals("us20005j32");
	}

	@Test
	public void testGetMagnitude() {
		assert e1.getMagnitude() == 7.8;
	}

	@Test
	public void testGetDescription() {
		assert e1.getDescription().equals("M 7.8 - 27km SSE of Muisne, Ecuador");
	}

	@Test
	public void testGetTime() {
		assert e1.getTime() == 1460851117280L;
	}

	@Test
	public void testGetNeatTimeFormat() {
		assert e1.getNeatDate().matches("^(\\w{3}\\s+)+\\d{2}\\s+(\\d{2}:){2}\\d{2}\\s+[A-Z]{3}\\s+\\d+");
	}

	@Test
	public void testHashCode() {
		assert e1.hashCode() == e2.hashCode();
	}

	@Test
	public void testHashCodeFailing() {
		assert e1.hashCode() != e3.hashCode();
	}

}

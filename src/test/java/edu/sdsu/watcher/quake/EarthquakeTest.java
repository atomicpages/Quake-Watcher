package edu.sdsu.watcher.quake;

import org.junit.BeforeClass;
import org.junit.Test;

public class EarthquakeTest {

	private static Earthquake e1, e2, e3, e4, e5, e6;

	@BeforeClass
	public static void setup() {
		e1 = new Earthquake(CoordinateTest.getC1(), 7.8, "M 7.8 - 27km SSE of Muisne, Ecuador", "us20005j32", 1460851117280L);
		e2 = new Earthquake(CoordinateTest.getC2(), 7.8, "M 7.8 - 27km SSE of Muisne, Ecuador", "us20005j32", 1460851117280L);
		e3 = new Earthquake(CoordinateTest.getC1(), 6.8, "M 7.8 - 27km SSE of Muisne, Ecuador", "us20005j32", 123L);
		e4 = new Earthquake(CoordinateTest.getC1(), 7.8, "M 7.8 - 27km SSE of Muisne, Ecuador", "us20005j32", 1560851117280L);
		e5 = new Earthquake(CoordinateTest.getC3(), 7.8, "M 7.8 - 27km SSE of Muisne, Ecuador", "us20005j32", 1460851117280L);
		e6 = new Earthquake(CoordinateTest.getC3(), 7.8, "M 7.8 - 123km SSE of Muisne, Ecuador", "us20005j32", 1460851117280L);
	}

	@Test
	public void testEquals() {
		assert e1.equals(e2);
	}

	@Test
	public void testEqualsNull() {
		assert e1 != null;
	}

	@Test
	public void testInequality() {
		assert !e1.equals(e3);
	}

	@Test
	public void testSameObjectEquals() {
		assert e1.equals(e1);
	}

	@Test
	public void testDiffObjects() {
		assert !e1.equals(new Object());
	}

	@Test
	public void testTimeDiff() {
		assert !e1.equals(e4);
	}

	@Test
	public void testCoordinateDiff() {
		assert !e1.equals(e5);
	}

	@Test
	public void testDescriptionDiff() {
		assert !e1.equals(e6);
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

	@Test
	public void testToString() {
		assert e1.toString().equals(e2.toString());
	}

}

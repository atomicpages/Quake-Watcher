package edu.sdsu.watcher.quake.structures;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.sdsu.watcher.quake.factories.DecodeJson;

public class QuakeStructTest {

	private static File resource = null;
	private static QuakeStruct struct = null;
	private static QuakeStruct.Features features = null;
	private static QuakeStruct.Features.Properties properties = null;
	private static QuakeStruct.Features.Geometry geometry = null;

	@BeforeClass
	public static void setup() throws IOException {
		resource = new File(QuakeStruct.class.getClassLoader().getResource("all_hour.json").getFile());
		struct = DecodeJson.parse(resource, QuakeStruct.class);
		features = struct.getFeatures().get(0);
		properties = features.getProperties();
		geometry = features.getGeometry();
	}

	@Test
	public void testType() {
		assert struct.getType().equals("FeatureCollection");
	}

	@Test
	public void testMetadataGetGenerated() {
		assert struct.getMetadata().getGenerated() == 1461399586000L;
	}

	@Test
	public void testMetadataGetUrl() {
		assert struct.getMetadata().getUrl().equals(
				"http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson"
		);
	}

	@Test
	public void testMetadataGetTitle() {
		assert struct.getMetadata().getTitle().equals("USGS All Earthquakes, Past Hour");
	}

	@Test
	public void testMetadataGetStatus() {
		assert struct.getMetadata().getStatus() == 200;
	}

	@Test
	public void testMetadataGetApi() {
		assert struct.getMetadata().getApi().equals("1.5.2");
	}

	@Test
	public void testMetadataGetCount() {
		assert struct.getMetadata().getCount() == 2;
	}

	@Test
	public void testGetFeaturesNotEmpty() {
		assert !struct.getFeatures().isEmpty();
	}

	@Test
	public void testFeaturesGetType() {
		assert features.getType().equals("Feature");
	}

	@Test
	public void testFeaturesGetId() {
		assert features.getId().equals("ci37335775");
	}

	@Test
	public void testFeaturesGetProperties() {
		assert features.getProperties() != null;
	}

	@Test
	public void testFeaturesGetGeometry() {
		assert features.getGeometry() != null;
	}

	@Test
	public void testFeaturePropertiesGetMag() {
		assert properties.getMag() == 0.93;
	}

	@Test
	public void testFeaturePropertiesGetPlace() {
		assert properties.getPlace().equals("5km SSE of Hemet, CA");
	}

	@Test
	public void testFeaturePropertiesGetTime() {
		assert properties.getTime() == 1461399067480L;
	}

	@Test
	public void testFeaturePropertiesGetUpdated() {
		assert properties.getUpdated() == 1461399363577L;
	}

	@Test
	public void testFeaturePropertiesGetTz() {
		assert properties.getTz() == -420;
	}

	@Test
	public void testFeaturePropertiesGetUrl() {
		assert properties.getUrl().equals("http://earthquake.usgs.gov/earthquakes/eventpage/ci37335775");
	}

	@Test
	public void testFeaturePropertiesGetDetail() {
		assert properties.getDetail().equals("http://earthquake.usgs.gov/earthquakes/feed/v1.0/detail/ci37335775.geojson");
	}

	@Test
	public void testFeaturePropertiesGetStatus() {
		assert properties.getStatus().equals("automatic");
	}

	@Test
	public void testFeaturePropertiesGetTsunami() {
		assert properties.getTsunami() == 0;
	}

	@Test
	public void testFeaturePropertiesGetSig() {
		assert properties.getSig() == 13;
	}

	@Test
	public void testFeaturePropertiesGetNet() {
		assert properties.getNet().equals("ci");
	}

	@Test
	public void testFeaturePropertiesGetCode() {
		assert properties.getCode().equals("37335775");
	}

	@Test
	public void testFeaturePropertiesGetIds() {
		assert properties.getIds().equals(",ci37335775,");
	}

	@Test
	public void testFeaturePropertiesGetSources() {
		assert properties.getSources().equals(",ci,");
	}

	@Test
	public void testFeaturePropertiesGetTypes() {
		assert properties.getTypes().equals(",general-link,geoserve,nearby-cities,origin,phase-data,scitech-link,");
	}

	@Test
	public void testFeaturePropertiesGetNst() {
		assert properties.getNst().equals(29);
	}

	@Test
	public void testFeaturePropertiesGetDmin() {
		assert properties.getDmin() == 0.03194;
	}

	@Test
	public void testFeaturePropertiesGetRms() {
		assert properties.getRms() == 0.13;
	}

	@Test
	public void testFeaturePropertiesGetGap() {
		assert properties.getGap() == 40;
	}

	@Test
	public void testFeaturePropertiesGetType() {
		assert properties.getType().equals("earthquake");
	}

	@Test
	public void testFeaturePropertiesGetTitle() {
		assert properties.getTitle().equals("M 0.9 - 5km SSE of Hemet, CA");
	}

	@Test
	public void testFeatureGeometryGetType() {
		assert geometry.getType().equals("Point");
	}

	@Test
	public void testFeatureGeometryGetCoordinates() {
		assert geometry.getCoordinates() != null;
	}

	@Test
	public void testFeatureGeometryCoordinatesGetLatitude() {
		assert geometry.getCoordinates().get(0) == -116.9568333;
	}

	@Test
	public void testFeatureGeometryCoordinatesGetLongitude() {
		assert geometry.getCoordinates().get(1) == 33.7033333;
	}

	@Test
	public void testFeatureGeometryCoordinatesGetDepth() {
		assert geometry.getCoordinates().get(2) == 15.06;
	}

	@Test
	public void testGetBbox() {
		assert struct.getBbox() != null && !struct.getBbox().isEmpty();
	}

	@Test
	public void testBbox() {
		assert struct.getBbox().get(0) == -121.043335;
		assert struct.getBbox().get(1) == 33.7033333;
		assert struct.getBbox().get(2) == 9.6;
		assert struct.getBbox().get(3) == -116.9568333;
		assert struct.getBbox().get(4) == 36.0444984;
		assert struct.getBbox().get(5) == 15.06;
	}

}

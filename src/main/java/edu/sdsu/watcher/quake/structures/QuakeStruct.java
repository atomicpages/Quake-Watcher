package edu.sdsu.watcher.quake.structures;

import java.util.ArrayList;
import java.util.List;

import edu.sdsu.watcher.quake.SimpleEarthquake;

/**
 * <p>Mirrors the structure of the {@code json} file we pull from usgs.gov to the tee.
 * The idea with this "struct" is that all the methods/values ultimately are read-only.
 * We only want one instance of this struct floating around at any given time
 * be it a multithreaded environment or otherwise.</p>
 * <p>A design choice of this class is to give users <strong>all</strong> information possible
 * that we're given from usgs. Additional processing is done in {@link SimpleEarthquake}
 * so we strip the "necessary" information for one particular use case.</p>
 */
public class QuakeStruct {

	private static final int COORD_LEN = 3;

	private String type;
	private List<Features> features = new ArrayList<>();
	private List<Double> bbox = new ArrayList<>();
	private Metadata metadata;

	private static class InstanceHolder {
		private static final QuakeStruct instance = new QuakeStruct();
	}

	public static QuakeStruct getInstance() {
		return InstanceHolder.instance;
	}

	public static class Metadata {
		private long generated;
		private String url, title, api;
		private short status;
		private int count;

		public final long getGenerated() {
			return this.generated;
		}

		public final String getUrl() {
			return this.url;
		}

		public final String getTitle() {
			return this.title;
		}

		public final String getApi() {
			return this.api;
		}

		public final short getStatus() {
			return this.status;
		}

		public final int getCount() {
			return this.count;
		}

	}

	public static class Features {
		private String type, id;
		private Properties properties;
		private Geometry geometry;

		public final String getType() {
			return this.type;
		}

		public final String getId() {
			return this.id;
		}

		public final Properties getProperties() {
			return this.properties;
		}

		public final Geometry getGeometry() {
			return this.geometry;
		}

		public static class Properties {
			private double mag, cdi, mmi, dmin, rms, gap;
			private long time, updated;
			private String place, url, detail, alert, status,
			net, code, ids, sources, types,
			type, title;
			private int tz, felt, tsunami, sig, nst;

			public final double getMag() {
				return this.mag;
			}

			public final double getCdi() {
				return this.cdi;
			}

			public final double getMmi() {
				return this.mmi;
			}

			public final double getDmin() {
				return this.dmin;
			}

			public final long getTime() {
				return this.time;
			}

			public final long getUpdated() {
				return this.updated;
			}

			public final String getPlace() {
				return this.place;
			}

			public final String getUrl() {
				return this.url;
			}

			public final String getDetail() {
				return this.detail;
			}

			public final String getAlert() {
				return this.alert;
			}

			public final String getStatus() {
				return this.status;
			}

			public final String getNet() {
				return this.net;
			}

			public final String getCode() {
				return this.code;
			}

			public final String getIds() {
				return this.ids;
			}

			public final String getSources() {
				return this.sources;
			}

			public final String getTypes() {
				return this.types;
			}

			public final String getType() {
				return this.type;
			}

			public final String getTitle() {
				return this.title;
			}

			public final int getTz() {
				return this.tz;
			}

			public final int getFelt() {
				return this.felt;
			}

			public final int getTsunami() {
				return this.tsunami;
			}

			public final int getSig() {
				return this.sig;
			}

			public final double getRms() {
				return this.rms;
			}

			public final double getGap() {
				return this.gap;
			}

			public final Object getNst() {
				return this.nst;
			}

		}
		public static class Geometry {
			private String type;
			private List<Double> coordinates = new ArrayList<>(COORD_LEN);

			public final String getType() {
				return this.type;
			}

			public final List<Double> getCoordinates() {
				return this.coordinates;
			}
		}
	}

	public final String getType() {
		return this.type;
	}

	public final List<Features> getFeatures() {
		return this.features;
	}

	public final Metadata getMetadata() {
		return this.metadata;
	}

	public final List<Double> getBbox() {
		return this.bbox;
	}

}

Quake Watcher
=============

Quake Watcher was born out of an existing project with a broken Java API. This version of Quake Watcher has an equally robust API that allows us to query data from usgs.gov while making sense of the data.

An example URL can be seen here: http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_week.geojson

## Features
* Real-time interaction with USGS data
* Easy-to-use API
* Fully documented
* Easily extensible to your own project


### Sample Usage
~~~java
import java.util.List;

public class Sample {

	public static void main(String[] args) {
		EarthquakeService usgs = new EarthquakeService();
		List<Earthquake> quakes = usgs.getEarthquakes("significant", "month");

		for(Earthquake quake : quakes) {
			System.out.println(quake.getDescription());
		}
	}

}
~~~

### TODO
1. Implement caching of `json` data
2. Add unit tests

### License
This project is released under the MIT license. See the `LICENSE` file for a copy of the license.

### Author
Created by Dennis Thompson for CS 496 at SDSU.

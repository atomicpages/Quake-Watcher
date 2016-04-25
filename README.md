Quake Watcher
=============

Quake Watcher was born out of an existing project with a cumbersome Java API. This version of Quake Watcher has an equally robust API that allows us to query data from usgs.gov, run analysis on the data, and cache the data for local processing.

An example URL can be seen here: http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson

## Features
* Real-time interaction with USGS data
* Easy-to-use API
* Fully documented
* Easily extensible to your own project
* Simple file caching system````


### Sample Usage
~~~java
import edu.sdsu.watcher.quake.EarthquakeService;

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

### License
This project is released under the MIT license. See the `LICENSE` file for a copy of the license.

### Author
Created by Dennis Thompson for the Computer Science department at [SDSU](http://www.cs.sdsu.edu/).

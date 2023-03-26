# fujitsu-internship-test-deliveryprice

## Data
Each class in the `databaseObjects` package either corresponds to a table in the database or is a controller or repository for such class.

A `WeatherData` object is used to store components that we need of the weather data from Estonian Environment Agency.

An `RBF` is used to store information about regional base fees paid out based on location.
Querying the table by `city` and `vehicle` gives us an entry that tells us what the `rbf` for that city and vehicle type is.
If no entry is found then our table is incomplete.

An `ATEF` is used to store information about extra fees paid out based on air temperature, where `maxtemp` indicates the temperature below which the courier qualifies for the extra fee.
Querying the table by `vehicle` and sorting by `maxtemp` in ascending order ensures that the first entry with a `maxtemp` higher than our current temperature tells us the `atef` the courier should be paid.
If no matching entry is found then no extra fee is given.

An `WSEF` is used to store information about extra fees paid out based on wind speed, where `minspeed` indicates the wind speed above which the courier qualifies for the extra fee.
Querying the table by `vehicle` and sorting by `minspeed` in descending order ensures that the first entry with a `minspeed` lower than our current temperature tells us the `wsef` the courier should be paid.
An entry of -1 in `wsef` means that the weather conditions are not suitable for this type of vehicle.
If no matching entry is found then no extra fee is given.

An `WPEF` is used to store information about extra fees paid out based on weather phenomena, where `phenomenon` indicates the weather phenomenon, matching which the courier qualifies for the extra fee.
Querying the table by `vehicle` and looking for matches with our weather phenomenon tells us the `wpef` the courier should be paid.
An entry of -1 in `wpef` means that the weather conditions are not suitable for this type of vehicle.
If no matching entry is found then no extra fee is given.

All tables except `WeatherData` have to be manually filled out by the operator.

## Database
The data is stored in an H2 database with the url `jdbc:h2:~/fujitsudeliveryprice`, user name `root` and no password. These parameters can be changed in the `application.properties` file, including the connection type.
The url format `jdbc:h2:tcp://localhost/~/fujitsudeliveryprice` allows for a tcp connection that permits connecting to the database even when another user is aloready connected.

## Operation
Once activated, the program remains running until stopped or a fatal error occurs.
At every time specified by the cron expression `cron.expresiion=` in `application.properties`, the `importWeatherData` method is called to read in data from `https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php` and store the data for the stations that interest us in the database.
If the cron expression is specified, then the default value of `0 15 * * * *` (every hour at HH:15:00) is used.

The webserver runs on port `8080` of `localhost`.
In order to query for the fees, a get request should be sent to the url `http://localhost:8080/deliveryfee/{City}/{Vehicle}` or `http://localhost:8080/deliveryfee/{City}/{Vehicle}/{Datetime}`,
where `{City}` has to be a city recognised by the program ('Tallinn', 'Tartu' or 'Pärnu') at the time of writing,
`{Vehicle}` has to be a vehicle recognised by the program ('Car', 'Scooter' or 'Bike') at the time of writing,
and the optional parameter `{Datetime}` has to be in a valid Java datetime format (`YYYY-MM-DDTHH:mm:ss`). Local time is always assumed.
The response will be the total fee in the form `.02f€` (a floating point number rounded to two decimal places, followed by the '€' symbol).

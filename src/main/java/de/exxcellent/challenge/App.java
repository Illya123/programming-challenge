package de.exxcellent.challenge;


import de.exxcellent.challenge.csv.CsvParser;

import static de.exxcellent.challenge.csv.CsvParser.CsvContent;
import static de.exxcellent.challenge.service.FootballService.footballService;
import static de.exxcellent.challenge.service.WeatherService.weatherService;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public final class App {

    /**
     * This is the main entry method of your program.
     *
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {

        // Additional thoughts: The interface from the CsvParser to the services I created is semi optimal,
        // because it consumes the CsvContent instead of an abstraction of some kind only
        // But on the other hand there could be a service that is made to consume a certain data format. CsvContent could be renamed to something else,
        // but more thought should be put into the design.
        // The biggest problem in my opinion is the knowledge about the domain. Without it, it is hard to formulate something concrete without going into
        // the wrong direction, because in my opinion the architecture is always at most as good as the knowledge about the corresponding domain.

        // I decided to go with services because they have a clear structure and meaning.
        // There are still quite a few checks on the data that need to be done. But I decided that I spent enough time on this task and I am somewhat
        // satisfied with my time to implementation ratio.

        // I didn't really go for a test driven approach, because I am just not used to it and don't know how to start properly (altough I know in theory).


        // Your preparation code …
        CsvContent weatherData = CsvParser.parseCsv(App.class.getResourceAsStream("weather.csv"));
        CsvContent footballData = CsvParser.parseCsv(App.class.getResourceAsStream("football.csv"));

        String dayWithSmallestTempSpread = weatherService().calculateDayWithSmallestTempSpread(weatherData);
        System.out.printf("Day with smallest temperature spread : %s%n", dayWithSmallestTempSpread);

        String teamWithSmallestGoalSpread = footballService().calculateTeamWithSmallestGoalSpread(footballData); // Your goal analysis function call …
        System.out.printf("Team with smallest goal spread       : %s%n", teamWithSmallestGoalSpread);
    }
}

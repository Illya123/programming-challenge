package de.exxcellent.challenge.service;


import java.util.HashMap;
import java.util.List;

import static de.exxcellent.challenge.constants.WeatherConstants.*;
import static de.exxcellent.challenge.csv.CsvParser.*;

/**
 * A simple service that analyzes given weather data.
 */
public class WeatherService {

    private static WeatherService instance;

    private WeatherService() {
    }

    /**
     * Singleton instance of the {@code WeatherService}
     */
    public static WeatherService weatherService() {
        if (instance == null) {
            instance = new WeatherService();
        }
        return instance;
    }

    /**
     * Calculates the day with the smallest temperature spread from a {@link CsvContent}.
     * It is assumed that the given {@code CsvContent} at least has headers with the names
     * <ul>
     *     <li>Day</li>
     *     <li>MxT</li>
     *     <li>MnT</li>
     * </ul>
     * @param weatherData that was parsed from a csv file ({@see CsvParser}
     * @return the numerical representation of a day (e.g. 28)
     */
    public String calculateDayWithSmallestTempSpread(CsvContent weatherData) {
        var days = weatherData.getValuesForHeader(DAY);
        var minTemps = weatherData.getValuesForHeader(MINIMUM_TEMPERATURE);
        var maxTemps = weatherData.getValuesForHeader(MAXIMUM_TEMPERATURE);

        return calculateDayWithSmallestTempSpread(days, minTemps, maxTemps);
    }

    /**
     * <p>Calculates the day with the smallest temperature spread.
     * This method assumes that all inputs are correlated and in the correct order.
     * </p>
     * E.g.: <br>
     *      Day: 1, MinTemp: 25, MaxTemp: 55 <br>
     *      Day: 2, MinTemp: 17, MaxTemp: 32
     *
     * @param days a list of numerical representation of days of a month (e.g. {1,2,3,4..30}
     * @param minTemps a list of minimum temperatures that correlate to the given list of days
     * @param maxTemps a list of maximum temperatures that correlate to the given list of days
     * @return the numerical representation of a day (e.g. 28)
     */
    public String calculateDayWithSmallestTempSpread(List<String> days, List<String> minTemps, List<String> maxTemps) {
        if (minTemps.size() != maxTemps.size() || maxTemps.size() != days.size())
            throw new IllegalArgumentException("The given data is incomplete");

        var tempDiffPerDay = new HashMap<Integer, String>();

        for (int i = 0; i < minTemps.size(); i++) {
            var difference = Integer.parseInt(maxTemps.get(i)) - Integer.parseInt(minTemps.get(i));
            tempDiffPerDay.put(difference, days.get(i));
        }

        var minDifference = tempDiffPerDay.keySet().stream().mapToInt(v -> v).min();

        return tempDiffPerDay.get(minDifference.getAsInt());
    }
}

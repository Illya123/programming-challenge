package de.exxcellent.challenge.service;

import de.exxcellent.challenge.csv.CsvParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.exxcellent.challenge.service.WeatherService.weatherService;
import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {


    @Test
    public void calculateSmallestSpread(){

        var days = List.of("1", "2", "3", "7");
        var minTemp = List.of("27", "33", "22", "15");
        var maxTemp = List.of("33", "35", "60", "19");
        var maxTempWrong = List.of("33", "35", "60", "19", "29");

        var dayWithSmallestTempSpread = weatherService().calculateDayWithSmallestTempSpread(days, minTemp, maxTemp);

        // Checks for data validation could be made in the service as well (like when min is higher than max)
        assertEquals("2", dayWithSmallestTempSpread);

        assertThrows(IllegalArgumentException.class, () -> weatherService().calculateDayWithSmallestTempSpread(days, minTemp, maxTempWrong));
    }

    @Test
    public void calculateSmallestSpreadFromCsv(){
        var csvContent = CsvParser.parseCsv(FootballServiceTest.class.getResourceAsStream("../weather.csv"));

        var teamWithSmallestGoalSpread = weatherService().calculateDayWithSmallestTempSpread(csvContent);

        assertEquals("14", teamWithSmallestGoalSpread);
    }

}
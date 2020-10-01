package de.exxcellent.challenge.service;

import de.exxcellent.challenge.csv.CsvParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.exxcellent.challenge.service.FootballService.footballService;
import static org.junit.jupiter.api.Assertions.*;

class FootballServiceTest {

    @Test
    public void calculateSmallestSpread(){

        List<String> teams = List.of("Noobs", "Pros", "Average guys");
        List<String> goals = List.of("3", "5", "7");
        List<String> allowedGoals = List.of("5", "3", "2");

        List<String> allowedGoalsWrong = List.of("7", "3", "8", "9");

        var teamWithSmallestGoalSpread = footballService().calculateTeamWithSmallestGoalSpread(teams, goals, allowedGoals);

        // Noobs and pros have the same allowed goals -> interface is not perfect here.
        assertEquals(teamWithSmallestGoalSpread, "Pros");
        assertThrows(IllegalArgumentException.class, () -> footballService().calculateTeamWithSmallestGoalSpread(teams, goals, allowedGoalsWrong));
    }

    @Test
    public void calculateSmallestSpreadFromCsv(){
        var csvContent = CsvParser.parseCsv(FootballServiceTest.class.getResourceAsStream("../football.csv"));

        var teamWithSmallestGoalSpread = footballService().calculateTeamWithSmallestGoalSpread(csvContent);

        assertEquals("Aston_Villa", teamWithSmallestGoalSpread);
    }

}
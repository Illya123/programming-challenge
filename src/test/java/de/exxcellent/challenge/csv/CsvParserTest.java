package de.exxcellent.challenge.csv;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CsvParserTest {


    @Test
    public void parseCsvSimple(){
        var csvContent = CsvParser.parseCsv(CsvParserTest.class.getResourceAsStream("../football.csv"));

        var team = csvContent.getValuesForHeader("Team");
        var games = csvContent.getValuesForHeader("Games");
        var wins = csvContent.getValuesForHeader("Wins");
        var losses = csvContent.getValuesForHeader("Losses");
        var goals = csvContent.getValuesForHeader("Goals");
        var goalsAllowed = csvContent.getValuesForHeader("Goals Allowed");
        var points = csvContent.getValuesForHeader("Points");

        assertNotNull(team);
        assertNotNull(games);
        assertNotNull(wins);
        assertNotNull(losses);
        assertNotNull(goals);
        assertNotNull(goalsAllowed);
        assertNotNull(points);

        assertEquals(team.size(), games.size());
        assertEquals(games.size(), wins.size());
        assertEquals(wins.size(), losses.size());
        assertEquals(losses.size(), goals.size());
        assertEquals(goals.size(), goalsAllowed.size());
        assertEquals(goalsAllowed.size(), points.size());

        assertThrows(IllegalArgumentException.class, () -> csvContent.getValuesForHeader("NotExisting"));
    }

}
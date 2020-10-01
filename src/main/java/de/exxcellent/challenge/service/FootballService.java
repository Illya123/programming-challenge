package de.exxcellent.challenge.service;

import de.exxcellent.challenge.csv.CsvParser.CsvContent;

import java.util.HashMap;
import java.util.List;

import static de.exxcellent.challenge.constants.FootballConstants.*;

/**
 * A simple service, that provides statistics about football.
 */
public class FootballService {

    private static FootballService instance;

    private FootballService() {
    }

    /**
     * A singleton instance of the {@code FootballService}
     */
    public static FootballService footballService() {
        if (instance == null) {
            instance = new FootballService();
        }
        return instance;
    }

    /**
     * Calculates the team with the smallest goal spread from a {@link CsvContent}.
     * It is assumed that the given {@code CsvContent} at least has headers with the names
     * <ul>
     *     <li>Team</li>
     *     <li>Goals</li>
     *     <li>Goals Allowed</li>
     * </ul>
     * @param footballData that was parsed from a csv file ({@see CsvParser}
     * @return the name of the team with the smallest goal spread
     */
    public String calculateTeamWithSmallestGoalSpread(CsvContent footballData) {
        var teams = footballData.getValuesForHeader(TEAM);
        var goals = footballData.getValuesForHeader(GOALS);
        var goalsAllowed = footballData.getValuesForHeader(GOALS_ALLOWED);

        return calculateTeamWithSmallestGoalSpread(teams, goals, goalsAllowed);
    }

    /**
     * <p>Calculates the day with the smallest goal spread.
     * This method assumes that all inputs are correlated and in the correct order.
     * </p>
     * E.g.: <br>
     *      Team: Arsenal, Goals: 30, Allowed Goals: 12 <br>
     *      Team: Newcastle, Goals: 17, Allowed Goals: 22
     *
     * @param teams a list of team names
     * @param goals a list of goals that correlates to the given list of teams
     * @param goalsAllowed a list of allowed goals that correlate to the given list of teams
     * @return the name of the team with the smallest goal spread
     */
    public String calculateTeamWithSmallestGoalSpread(List<String> teams, List<String> goals, List<String> goalsAllowed) {
        if (goals.size() != goalsAllowed.size() || goalsAllowed.size() != teams.size())
            throw new IllegalArgumentException("The given data is incomplete");

        var spreadPerTeam = new HashMap<Integer, String>();

        for (int i = 0; i < teams.size(); i++) {
            var difference = Math.abs(Integer.parseInt(goalsAllowed.get(i)) - Integer.parseInt(goals.get(i)));
            spreadPerTeam.put(difference, teams.get(i));
        }

        var minDifference = spreadPerTeam.keySet().stream().mapToInt(v -> v).min();

        return spreadPerTeam.get(minDifference.getAsInt());
    }
}

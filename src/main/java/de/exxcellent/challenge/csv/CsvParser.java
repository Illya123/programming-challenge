package de.exxcellent.challenge.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * A simple csv parser that parses each csv file as a {@link CsvContent}.
 */
public class CsvParser {

    private static final String DELIMITER = ",";

    /**
     * Note: Inputstream will be closed inside the method
     *
     * @param csv
     * @return
     */
    public static CsvContent parseCsv(InputStream csv) {
        List<String> lines;

        try (csv) {
            lines = new BufferedReader(new InputStreamReader(csv, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("The given input could not be processed");
        }

        return new CsvContent(fillContent(lines));
    }

    private static Map<String, List<String>> fillContent(List<String> lines) {
        var content = new HashMap<String, List<String>>();
        var headers = Arrays.asList(lines.remove(0).split(DELIMITER));

        for (String line : lines) {
            var cellValues = Arrays.asList(line.split(DELIMITER));

            for (int i = 0; i < headers.size(); i++) {
                if (!content.containsKey(headers.get(i))) {
                    content.put(headers.get(i), new ArrayList<>(List.of(cellValues.get(i))));
                } else {
                    content.get(headers.get(i)).add(cellValues.get(i));
                }
            }
        }
        return content;
    }

    /**
     * {@code CsvContent} is a simple abstraction of the main features of a csv file.
     * It represents the csv file as Map of csv headers where each header holds a list of its cell values
     */
    public static class CsvContent {

        private final Map<String, List<String>> csvContent;

        /**
         * constructor
         *
         * @param csvContent representation of a csv file
         */
        public CsvContent(Map<String, List<String>> csvContent) {
            this.csvContent = csvContent;
        }

        public List<String> getValuesForHeader(String header) {
            if (!csvContent.containsKey(header)) throw new IllegalArgumentException(format("No csv header found for value: %s", header));

            return csvContent.get(header);
        }
    }
}

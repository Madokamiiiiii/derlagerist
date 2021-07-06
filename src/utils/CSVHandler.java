package utils;

import Models.Assignment;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class CSVHandler {

    public static List<Assignment> getAssignmentsFromCSV() {
        List<Assignment> assignments = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Path.of("src/", "Leistungsnachweis.csv"), StandardCharsets.ISO_8859_1);
            lines.remove(0); // We don't need the header

            lines.forEach(line -> {
                String[] lineElements = line.split(";");

            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

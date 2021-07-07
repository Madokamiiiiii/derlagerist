package utils;

import models.Assignment;
import models.Product;
import models.products.Paper;
import models.products.Stone;
import models.products.Wood;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CSVHandler {

    public static List<Assignment> getAssignmentsFromCSV() {
        List<Assignment> assignments = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Path.of("src/", "Leistungsnachweis.csv"), StandardCharsets.ISO_8859_1);
            lines.remove(0); // We don't need the header

            lines.forEach(line -> {
                String[] lineElements = line.split(";");

                Product product = switch (lineElements[2]) {
                    case "Papier" -> createPaperFromLine(lineElements);
                    case "Holz" -> createWoodFromLine(lineElements);
                    case "Stein" -> createStoneFromLine(lineElements);
                    default -> throw new IllegalStateException("Unexpected value: " + lineElements[2]);
                };

                Assignment.Type type = switch (lineElements[1]) {
                    case "Einlagerung" -> Assignment.Type.WAREHOUSING;
                    case "Auslagerung" -> Assignment.Type.DELIVERING;
                    default -> throw new IllegalStateException("Unexpected value: " + lineElements[1]);
                };

                assignments.add(new Assignment(product, Integer.parseInt(lineElements[5]), type));
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return assignments;
    }


    private static Paper createPaperFromLine(String[] line) {
        Paper.Color color = switch (line[3]) {
            case "Weiß" -> Paper.Color.WHITE;
            case "Blau" -> Paper.Color.BLUE;
            case "Grün" -> Paper.Color.GREEN;
            default -> throw new IllegalArgumentException("Something's wrong with the paper colors in the CSV.");
        };

        Paper.Size size = switch (line[4]) {
            case "A3" -> Paper.Size.A3;
            case "A4" -> Paper.Size.A4;
            case "A5" -> Paper.Size.A5;
            default -> throw new IllegalArgumentException("Something's wrong with the paper sizes in the CSV.");
        };

        return new Paper(color, size);
    }

    public static Wood createWoodFromLine(String[] line) {
        Wood.Type type = switch (line[3]) {
            case "Kiefer" -> Wood.Type.PINE;
            case "Buche" -> Wood.Type.BEECH;
            case "Eiche" -> Wood.Type.OAK;
            default -> throw new IllegalArgumentException("Something's wrong with the wood types in the CSV.");
        };

        Wood.Form form = switch (line[4]) {
            case "Bretter" -> Wood.Form.PLANK;
            case "Balken" -> Wood.Form.TIMBER;
            case "Scheit" -> Wood.Form.LOG;
            default -> throw new IllegalArgumentException("Something's wrong with the wood forms in the CSV.");
        };

        return new Wood(type, form);
    }

    private static Product createStoneFromLine(String[] line) {
        Stone.Type type = switch (line[3]) {
            case "Marmor" -> Stone.Type.MARBLE;
            case "Granit" -> Stone.Type.GRANITE;
            case "Sandstein" -> Stone.Type.SANDSTONE;
            default -> throw new IllegalArgumentException("Something's wrong with the stone types in the CSV.");
        };

        Stone.Weight weight = switch (line[4]) {
            case "Leicht" -> Stone.Weight.LIGHT;
            case "Mittel" -> Stone.Weight.MIDDLE;
            case "Schwer" -> Stone.Weight.HEAVY;
            default -> throw new IllegalArgumentException("Something's wrong with the stone weights in the CSV.");
        };

        return new Stone(type, weight);
    }
}

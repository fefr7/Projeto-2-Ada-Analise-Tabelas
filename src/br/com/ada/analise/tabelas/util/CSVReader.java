
package br.com.ada.analise.tabelas.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class CSVReader {

    private static final String RESOURCES_PATH = "resources/";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String DELIMITER = ","; 
    
    public static <T> List<T> read(String filename, Function<String[], T> mapper) throws IOException {
        String filePath = RESOURCES_PATH + filename;
        
        try (Stream<String> lines = Files.lines(Paths.get(filePath), StandardCharsets.ISO_8859_1)) { 
            return lines
                    .skip(1) 
                    .map(line -> line.split(DELIMITER, -1))
                    .filter(columns -> columns.length > 1) 
                    .map(mapper) 
                    .toList(); 
        }
    }

public static LocalDate parseDate(String dateStr) {
    try {
        if (dateStr == null) return null;
        // Remove aspas e espaços (inclusive invisíveis)
        String cleanDate = dateStr.replace("\"", "").replace("\u00A0", "").trim();
        if (cleanDate.isEmpty()) return null;
        return LocalDate.parse(cleanDate, DATE_FORMATTER);
    } catch (Exception e) {
        return null;
    }
}


    public static int parseInt(String intStr) {
        try {
            String cleanedStr = intStr.replace("\"", "").trim(); 
            return Integer.parseInt(cleanedStr); 
        } catch (NumberFormatException e) {
            return 0; 
        }
    }

    public static String clean(String text) {
        if (text == null) return null;
        return text.replace("\"", "").replace("\u00A0", "").trim();
    }
    
}

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
    private static final String DELIMITER = ","; // CORREÇÃO: Uso de ponto e vírgula como delimitador
    
    public static <T> List<T> read(String filename, Function<String[], T> mapper) throws IOException {
        String filePath = RESOURCES_PATH + filename;
        
        try (Stream<String> lines = Files.lines(Paths.get(filePath), StandardCharsets.ISO_8859_1)) { // CORREÇÃO: Encoding
            return lines
                    .skip(1) 
                    .map(line -> line.split(DELIMITER, -1)) // Usa o delimitador ;
                    .filter(columns -> columns.length > 1) 
                    .map(mapper) 
                    .toList(); 
        }
    }

    public static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (Exception e) {
            return null; 
        }
    }

    public static int parseInt(String intStr) {
        try {
            String cleanedStr = intStr.replace("\"", "").trim(); // Lida com aspas duplas
            return Integer.parseInt(cleanedStr); 
        } catch (NumberFormatException e) {
            return 0; 
        }
    }
}
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Клас DataFileHandler управляє роботою з файлами даних LocalDateTime.
 */
public class DataFileHandler {
    /**
     * Завантажує масив об'єктів LocalDateTime з файлу.
     * 
     * @param filePath Шлях до файлу з даними.
     * @return Масив об'єктів LocalDateTime.
     */
    public static LocalDateTime[] loadArrayFromFile(String filePath) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime[] temporaryArray = new LocalDateTime[1000];
        int currentIndex = 0;

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = fileReader.readLine()) != null) {
                // Видаляємо можливі невидимі символи та BOM
                currentLine = currentLine.trim().replaceAll("^\\uFEFF", "");
                if (!currentLine.isEmpty()) {
                    LocalDateTime parsedDateTime = LocalDateTime.parse(currentLine, timeFormatter);
                    temporaryArray[currentIndex++] = parsedDateTime;
                }
            }
        } catch (IOException ioException) {
            throw new java.io.UncheckedIOException("Не вдалося прочитати " + filePath, ioException);
        }

        LocalDateTime[] resultArray = new LocalDateTime[currentIndex];
        System.arraycopy(temporaryArray, 0, resultArray, 0, currentIndex);

        return resultArray;
    }

    /**
     * Зберігає масив об'єктів LocalDateTime у файл.
     * 
     * @param dateTimeArray Масив об'єктів LocalDateTime.
     * @param filePath Шлях до файлу для збереження.
     */
    public static void writeArrayToFile(LocalDateTime[] dateTimeArray, String filePath) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (LocalDateTime dateTimeElement : dateTimeArray) {
                fileWriter.write(dateTimeElement.toString());
                fileWriter.newLine();
            }
        } catch (IOException ioException) {
            throw new java.io.UncheckedIOException("Не вдалося записати " + filePath, ioException);
        }
    }
}

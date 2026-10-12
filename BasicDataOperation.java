import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Path;

/** Координатор пошуку точного значення та значень у заданому інтервалі. */
public class BasicDataOperation {
    static final String PATH_TO_DATA_FILE = "list/LocalDateTime.data";
    static final String SORTED_FILE_PREFIX = "list/LocalDateTime.data.sorted";

    public static void main(String[] args) {
        if (args.length != 3 && args.length != 4) {
            System.err.println("Використання: java BasicDataOperation <значення> <нижня-межа> <верхня-межа> [файл-даних]");
            System.err.println("Приклад: java BasicDataOperation 2026-06-21T09:46:21 2026-06-01T00:00:00 2026-08-31T23:59:59");
            System.exit(1);
            return;
        }
        LocalDateTime searched;
        LocalDateTime lower;
        LocalDateTime upper;
        try {
            searched = LocalDateTime.parse(args[0], DateTimeFormatter.ISO_DATE_TIME);
            lower = LocalDateTime.parse(args[1], DateTimeFormatter.ISO_DATE_TIME);
            upper = LocalDateTime.parse(args[2], DateTimeFormatter.ISO_DATE_TIME);
            if (lower.isAfter(upper)) {
                throw new IllegalArgumentException("Нижня межа перевищує верхню");
            }
        } catch (RuntimeException exception) {
            System.err.println("Некоректні аргументи: " + exception.getMessage());
            System.exit(1);
            return;
        }

        String file = args.length == 4 ? args[3] : PATH_TO_DATA_FILE;
        try {
            LocalDateTime[] values = DataFileHandler.loadArrayFromFile(file);
            System.out.println("Точне значення для пошуку: " + searched);
            System.out.println("Завантажено значень: " + values.length);
            System.out.println("LIST");
            new BasicDataOperationUsingList(searched, values).executeDataOperations(lower, upper);
            System.out.println("SET");
            new BasicDataOperationUsingSet(searched, values).executeDataAnalysis();
            System.out.println("QUEUE");
            new BasicDataOperationUsingQueue(searched, values).runDataProcessing();
        } catch (Exception exception) {
            System.err.println("Помилка обробки даних: " + exception.getMessage());
            System.exit(1);
        }
    }
}

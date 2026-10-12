import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Клас BasicDataOperationUsingSet реалізує операції з множиною HashSet для LocalDateTime.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataAnalysis()} - Запускає аналіз даних.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив LocalDateTime.</li>
 *   <li>{@link #findInArray()} - Пошук значення в масиві LocalDateTime.</li>
 *   <li>{@link #locateMinMaxInArray()} - Знаходить граничні значення в масиві.</li>
 *   <li>{@link #findInSet()} - Пошук значення в множині LocalDateTime.</li>
 *   <li>{@link #locateMinMaxInSet()} - Знаходить мінімальне і максимальне значення в множині.</li>
 *   <li>{@link #analyzeArrayAndSet()} - Аналізує елементи масиву та множини.</li>
 * </ul>
 */
public class BasicDataOperationUsingSet {
    private boolean arraySorted = false;
    LocalDateTime dateTimeValueToSearch;
    LocalDateTime[] dateTimeArray;
    Set<LocalDateTime> dateTimeSet = new HashSet<>();

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param dateTimeValueToSearch Значення для пошуку
     * @param dateTimeArray Масив LocalDateTime
     */
    BasicDataOperationUsingSet(LocalDateTime dateTimeValueToSearch, LocalDateTime[] dateTimeArray) {
        this.dateTimeValueToSearch = dateTimeValueToSearch;
        this.dateTimeArray = dateTimeArray.clone();
        this.dateTimeSet = new HashSet<>(Arrays.asList(dateTimeArray));
    }
    
    /**
     * Запускає комплексний аналіз даних з використанням множини HashSet.
     * 
     * Метод завантажує дані, виконує операції з множиною та масивом LocalDateTime.
     */
    public void executeDataAnalysis() {
        // спочатку аналізуємо множину дати та часу
        System.out.println("Set: до сортування копії даних у масиві");
        findInSet();
        locateMinMaxInSet();
        analyzeArrayAndSet();

        // потім обробляємо масив
        System.out.println("Масив: до сортування");
        findInArray();
        locateMinMaxInArray();

        performArraySorting();

        System.out.println("Set: після сортування копії даних у масиві");
        findInSet();
        locateMinMaxInSet();
        System.out.println("Масив: після сортування");

        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до файлу
        DataFileHandler.writeArrayToFile(dateTimeArray, BasicDataOperation.SORTED_FILE_PREFIX + ".set.sorted");
    }

    /**
     * Упорядковує масив об'єктів LocalDateTime за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    private void performArraySorting() {
        long timeStart = System.nanoTime();

        Arrays.sort(dateTimeArray);
        arraySorted = true;

        PerformanceTracker.displayOperationTime(timeStart, "сортування масиву дати i часу");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    private void findInArray() {
        long timeStart = System.nanoTime();

        int position = -1;
        if (arraySorted) {
            position = Arrays.binarySearch(dateTimeArray, dateTimeValueToSearch);
        } else {
            for (int i = 0; i < dateTimeArray.length; i++) {
                if (dateTimeArray[i].compareTo(dateTimeValueToSearch) == 0) {
                    position = i;
                    break;
                }
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в масивi дати i часу");

        if (position >= 0) {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' знайдено в масивi за позицією: " + position);
        } else {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' відсутній в масиві.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в масиві LocalDateTime.
     */
    private void locateMinMaxInArray() {
        if (dateTimeArray == null || dateTimeArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        LocalDateTime minValue = dateTimeArray[0];
        LocalDateTime maxValue = dateTimeArray[0];

        for (LocalDateTime currentDateTime : dateTimeArray) {
            if (currentDateTime.isBefore(minValue)) {
                minValue = currentDateTime;
            }
            if (currentDateTime.isAfter(maxValue)) {
                maxValue = currentDateTime;
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в масивi");

        System.out.println("Найменше значення в масивi: " + minValue);
        System.out.println("Найбільше значення в масивi: " + maxValue);
    }

    /**
     * Здійснює пошук конкретного значення в множині дати та часу.
     */
    private void findInSet() {
        long timeStart = System.nanoTime();

        boolean elementExists = this.dateTimeSet.contains(dateTimeValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в HashSet дати i часу");

        if (elementExists) {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' знайдено в HashSet");
        } else {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' відсутній в HashSet.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в множині LocalDateTime.
     */
    private void locateMinMaxInSet() {
        if (dateTimeSet == null || dateTimeSet.isEmpty()) {
            System.out.println("HashSet є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        LocalDateTime minValue = Collections.min(dateTimeSet);
        LocalDateTime maxValue = Collections.max(dateTimeSet);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в HashSet");

        System.out.println("Найменше значення в HashSet: " + minValue);
        System.out.println("Найбільше значення в HashSet: " + maxValue);
    }

    /**
     * Аналізує та порівнює елементи масиву та множини.
     */
    private void analyzeArrayAndSet() {
        System.out.println("Кiлькiсть елементiв в масивi: " + dateTimeArray.length);
        System.out.println("Кiлькiсть елементiв в HashSet: " + dateTimeSet.size());

        boolean allElementsPresent = true;
        for (LocalDateTime dateTimeElement : dateTimeArray) {
            if (!dateTimeSet.contains(dateTimeElement)) {
                allElementsPresent = false;
                break;
            }
        }

        if (allElementsPresent) {
            System.out.println("Всi елементи масиву наявні в HashSet.");
        } else {
            System.out.println("Не всi елементи масиву наявні в HashSet.");
        }
    }


}

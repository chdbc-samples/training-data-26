import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Клас BasicDataOperationsUsingSet реалізує операції з множиною HashSet для LocalDateTime.
 *
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataAnalysis()} - Запускає аналіз даних.</li>
 *   <li>{@link #findInSet()} - Пошук значення в множині LocalDateTime.</li>
 *   <li>{@link #locateMinMaxInSet()} - Знаходить мінімальне і максимальне значення в множині.</li>
 *   <li>{@link #analyzeDuplicates()} - Порівнює кількість вхідних значень і елементів множини.</li>
 * </ul>
 */
public class BasicDataOperationsUsingSet {
    LocalDateTime dateTimeValueToSearch;
    int sourceValueCount;
    Set<LocalDateTime> dateTimeSet = new HashSet<>();

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     *
     * @param dateTimeValueToSearch Значення для пошуку
     * @param dateTimeArray Масив LocalDateTime
     */
    BasicDataOperationsUsingSet(LocalDateTime dateTimeValueToSearch, LocalDateTime[] dateTimeArray) {
        this.dateTimeValueToSearch = dateTimeValueToSearch;
        this.sourceValueCount = dateTimeArray.length;
        this.dateTimeSet = new HashSet<>(Arrays.asList(dateTimeArray));
    }

    /**
     * Запускає комплексний аналіз даних з використанням множини HashSet.
     *
     * Метод виконує пошук, визначення граничних значень і аналіз повторів у множині LocalDateTime.
     */
    public void executeDataAnalysis() {
        findInSet();
        locateMinMaxInSet();
        analyzeDuplicates();

        // зберігаємо до файлу відсортовані унікальні значення з HashSet (без повторів)
        LocalDateTime[] sortedSetValues = dateTimeSet.toArray(new LocalDateTime[0]);
        Arrays.sort(sortedSetValues);
        DataFileHandler.writeArrayToFile(sortedSetValues, BasicDataOperations.SORTED_FILE_PREFIX + ".set.data");
    }

    /**
     * Здійснює пошук конкретного значення в множині дати та часу.
     */
    private void findInSet() {
        // назву операції будуємо до заміру, щоб склеювання рядка не потрапило у вимір
        String operationName = "пошук елемента '" + dateTimeValueToSearch + "' в HashSet<LocalDateTime>";
        long timeStart = System.nanoTime();

        // HashSet не перебирає елементи. contains() обчислює hashCode() шуканої дати,
        // за ним одразу визначає «кошик» (bucket) внутрішньої хеш-таблиці і через equals()
        // порівнює лише ті кілька елементів, що потрапили в цей кошик.
        // У середньому це O(1) незалежно від кількості елементів.
        boolean elementExists = this.dateTimeSet.contains(dateTimeValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, operationName);

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

        // HashSet розкладає елементи за хеш-кодами, а не за порядком дат, тому найменша
        // і найбільша дати можуть лежати будь-де. Collections.min() і Collections.max()
        // обходять усю множину ітератором і порівнюють елементи через compareTo(); кожен виклик O(n).
        LocalDateTime minValue = Collections.min(dateTimeSet);
        LocalDateTime maxValue = Collections.max(dateTimeSet);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в HashSet<LocalDateTime>");

        System.out.println("Найменше значення в HashSet: " + minValue);
        System.out.println("Найбільше значення в HashSet: " + maxValue);
    }

    /**
     * Порівнює кількість вхідних значень і елементів множини: різниця — це відкинуті повтори.
     */
    private void analyzeDuplicates() {
        System.out.println("Кiлькiсть вхiдних значень: " + sourceValueCount);
        System.out.println("Кiлькiсть елементiв в HashSet: " + dateTimeSet.size());
        System.out.println("Повторних входжень, вiдкинутих HashSet: " + (sourceValueCount - dateTimeSet.size()));
    }
}

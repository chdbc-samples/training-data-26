import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Клас BasicDataOperationUsingList реалізує операції з колекціями типу ArrayList для даних LocalDateTime.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив елементів LocalDateTime.</li>
 *   <li>{@link #findInArray()} - Здійснює пошук елемента в масиві LocalDateTime.</li>
 *   <li>{@link #locateMinMaxInArray()} - Визначає найменше і найбільше значення в масиві.</li>
 *   <li>{@link #sortList()} - Сортує колекцію List з LocalDateTime.</li>
 *   <li>{@link #findInList()} - Пошук конкретного значення в списку.</li>
 *   <li>{@link #locateMinMaxInList()} - Пошук мінімального і максимального значення в списку.</li>
 * </ul>
 */
public class BasicDataOperationUsingList {
    private boolean listSorted = false;
    private boolean arraySorted = false;
    private LocalDateTime dateTimeValueToSearch;
    private LocalDateTime[] dateTimeArray;
    private List<LocalDateTime> dateTimeList;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param dateTimeValueToSearch Значення для пошуку
     * @param dateTimeArray Масив LocalDateTime
     */
    BasicDataOperationUsingList(LocalDateTime dateTimeValueToSearch, LocalDateTime[] dateTimeArray) {
        this.dateTimeValueToSearch = dateTimeValueToSearch;
        this.dateTimeArray = dateTimeArray.clone();
        this.dateTimeList = new ArrayList<>(Arrays.asList(dateTimeArray));
    }
    
    /**
     * Виконує комплексні операції з структурами даних.
     * 
     * Метод завантажує масив і список об'єктів LocalDateTime, 
     * здійснює сортування та пошукові операції.
     */
    public void executeDataOperations(LocalDateTime lower, LocalDateTime upper) {
        // спочатку працюємо з колекцією List
        System.out.println("List: до сортування");
        analyzeCondition(dateTimeList, listSorted, lower, upper);
        findInList();
        locateMinMaxInList();
        
        sortList();
        System.out.println("List: після сортування");
        analyzeCondition(dateTimeList, listSorted, lower, upper);
        
        findInList();
        locateMinMaxInList();

        // потім обробляємо масив дати та часу
        System.out.println("Масив: до сортування");
        analyzeCondition(dateTimeArray, arraySorted, lower, upper);
        findInArray();
        locateMinMaxInArray();

        performArraySorting();
        System.out.println("Масив: після сортування");
        analyzeCondition(dateTimeArray, arraySorted, lower, upper);
        
        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до окремого файлу
        DataFileHandler.writeArrayToFile(dateTimeArray, BasicDataOperation.SORTED_FILE_PREFIX + ".list.sorted");
    }

    /**
     * Упорядковує масив об'єктів LocalDateTime за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    void performArraySorting() {
        long timeStart = System.nanoTime();

        Arrays.sort(dateTimeArray);
        arraySorted = true;

        PerformanceTracker.displayOperationTime(timeStart, "сортування масиву дати i часу");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    void findInArray() {
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
     * Визначає найменше та найбільше значення в масиві дати та часу.
     */
    void locateMinMaxInArray() {
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
     * Шукає конкретне значення дати та часу в колекції ArrayList.
     */
    void findInList() {
        long timeStart = System.nanoTime();

        int position = -1;
        if (listSorted) {
            position = Collections.binarySearch(dateTimeList, dateTimeValueToSearch);
        } else {
            int index = 0;
            for (LocalDateTime value : dateTimeList) {
                if (value.compareTo(dateTimeValueToSearch) == 0) {
                    position = index;
                    break;
                }
                index++;
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в List дати i часу");        

        if (position >= 0) {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' знайдено в ArrayList за позицією: " + position);
        } else {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' відсутній в ArrayList.");
        }
    }

    /**
     * Визначає найменше і найбільше значення в колекції ArrayList з датами.
     */
    void locateMinMaxInList() {
        if (dateTimeList == null || dateTimeList.isEmpty()) {
            System.out.println("Колекція ArrayList є пустою або не ініціалізованою.");
            return;
        }

        long timeStart = System.nanoTime();

        LocalDateTime minValue = Collections.min(dateTimeList);
        LocalDateTime maxValue = Collections.max(dateTimeList);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в List");

        System.out.println("Найменше значення в List: " + minValue);
        System.out.println("Найбільше значення в List: " + maxValue);
    }

    /**
     * Упорядковує колекцію List з об'єктами LocalDateTime за зростанням.
     * Відстежує та виводить час виконання операції сортування.
     */
    void sortList() {
        long timeStart = System.nanoTime();

        Collections.sort(dateTimeList);
        listSorted = true;

        PerformanceTracker.displayOperationTime(timeStart, "сортування ArrayList дати i часу");
    }

    // BEGIN CONDITION
    /** вибірка даних; sorted=true лише для даних, упорядкованих за зростанням. */
    public int analyzeCondition(LocalDateTime[] values, boolean sorted, LocalDateTime lower, LocalDateTime upper) {
        if (lower.isAfter(upper)) throw new IllegalArgumentException("Нижня межа перевищує верхню");
        String conditionDescription = "значення в інтервалі [" + lower + "; " + upper + "] включно";
        String phase = sorted ? "після сортування" : "до сортування";
        System.out.println("Масив: вибірка даних " + phase + ". Умова: " + conditionDescription);
        long timeStart = System.nanoTime();
        LocalDateTime[] matches = new LocalDateTime[values.length];
        int count = 0;
        int inspected = 0;
        if (!sorted) {
            // Невпорядковані дані: перевіряємо кожне входження.
            for (LocalDateTime value : values) {
                inspected++;
                if (!value.isBefore(lower) && !value.isAfter(upper)) matches[count++] = value;
            }
        } else {
            // Від найменших значень до верхньої межі.
            for (int i = 0; i < values.length; i++) {
                LocalDateTime value = values[i];
                inspected++;
                if (value.isAfter(upper)) break;
                if (!value.isBefore(lower) && !value.isAfter(upper)) matches[count++] = value;
            }
        }
        PerformanceTracker.displayOperationTime(timeStart, "вибірка даних: Масив, " + phase);
        // Друк результатів не входить у виміряний час пошуку.
        System.out.println("Переглянуто елементів: " + inspected);
        for (int i = 0; i < count; i++) System.out.println(matches[i]);
        System.out.println("У масиві знайдено " + count + " входжень (повтори враховано). Умова: " + conditionDescription + ".");
        return count;
    }

    /** вибірка даних; sorted=true лише для даних, упорядкованих за зростанням. */
    public int analyzeCondition(List<LocalDateTime> values, boolean sorted, LocalDateTime lower, LocalDateTime upper) {
        if (lower.isAfter(upper)) throw new IllegalArgumentException("Нижня межа перевищує верхню");
        String conditionDescription = "значення в інтервалі [" + lower + "; " + upper + "] включно";
        String phase = sorted ? "після сортування" : "до сортування";
        System.out.println("List: вибірка даних " + phase + ". Умова: " + conditionDescription);
        long timeStart = System.nanoTime();
        LocalDateTime[] matches = new LocalDateTime[values.size()];
        int count = 0;
        int inspected = 0;
        if (!sorted) {
            // Невпорядковані дані: перевіряємо кожне входження.
            for (LocalDateTime value : values) {
                inspected++;
                if (!value.isBefore(lower) && !value.isAfter(upper)) matches[count++] = value;
            }
        } else {
            // Від найменших значень до верхньої межі.
            java.util.ListIterator<LocalDateTime> cursor0 = values.listIterator(0);
            while (cursor0.hasNext()) {
                LocalDateTime value = cursor0.next();
                inspected++;
                if (value.isAfter(upper)) break;
                if (!value.isBefore(lower) && !value.isAfter(upper)) matches[count++] = value;
            }
        }
        PerformanceTracker.displayOperationTime(timeStart, "вибірка даних: List, " + phase);
        // Друк результатів не входить у виміряний час пошуку.
        System.out.println("Переглянуто елементів: " + inspected);
        for (int i = 0; i < count; i++) System.out.println(matches[i]);
        System.out.println("У List знайдено " + count + " входжень (повтори враховано). Умова: " + conditionDescription + ".");
        return count;
    }
    // END CONDITION
}

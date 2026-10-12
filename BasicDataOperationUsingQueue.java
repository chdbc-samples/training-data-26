import java.time.LocalDateTime;
import java.util.Queue;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Клас BasicDataOperationUsingQueue реалізує роботу з колекціями типу Queue для LocalDateTime.
 * 
 * <p>Основні функції класу:</p>
 * <ul>
 *   <li>{@link #runDataProcessing()} - Запускає комплекс операцій з даними.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив LocalDateTime.</li>
 *   <li>{@link #findInArray()} - Пошук значення в масиві LocalDateTime.</li>
 *   <li>{@link #locateMinMaxInArray()} - Знаходить мінімальне і максимальне значення в масиві.</li>
 *   <li>{@link #findInQueue()} - Пошук значення в черзі LocalDateTime.</li>
 *   <li>{@link #locateMinMaxInQueue()} - Знаходить граничні значення в черзі.</li>
 *   <li>{@link #performQueueOperations()} - Виконує операції peek і poll з чергою.</li>
 * </ul>
 * 
 */
public class BasicDataOperationUsingQueue {
    private boolean arraySorted = false;
    private LocalDateTime dateTimeValueToSearch;
    private LocalDateTime[] dateTimeArray;
    private Queue<LocalDateTime> dateTimeQueue;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param dateTimeValueToSearch Значення для пошуку
     * @param dateTimeArray Масив LocalDateTime
     */
    BasicDataOperationUsingQueue(LocalDateTime dateTimeValueToSearch, LocalDateTime[] dateTimeArray) {
        this.dateTimeValueToSearch = dateTimeValueToSearch;
        this.dateTimeArray = dateTimeArray.clone();
        this.dateTimeQueue = new PriorityQueue<>(Arrays.asList(dateTimeArray));
    }
    
    /**
     * Запускає комплексну обробку даних з використанням черги.
     * 
     * Метод завантажує дані, виконує операції з чергою та масивом LocalDateTime.
     */
    public void runDataProcessing() {
        // спочатку обробляємо чергу дати та часу
        System.out.println("Queue: до сортування копії даних у масиві");
        findInQueue();
        locateMinMaxInQueue();

        // потім працюємо з масивом
        System.out.println("Масив: до сортування");
        findInArray();
        locateMinMaxInArray();

        performArraySorting();

        System.out.println("Queue: після сортування копії даних у масиві");
        findInQueue();
        locateMinMaxInQueue();
        System.out.println("Масив: після сортування");

        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до файлу
        DataFileHandler.writeArrayToFile(dateTimeArray, BasicDataOperation.SORTED_FILE_PREFIX + ".queue.sorted");

        // Вилучення виконуємо після всіх порівнянь, щоб склад даних був однаковий.
        performQueueOperations();
    }

    /**
     * Сортує масив об'єктiв LocalDateTime та виводить початковий i вiдсортований масиви.
     * Вимiрює та виводить час, витрачений на сортування масиву в наносекундах.
     */
    private void performArraySorting() {
        // вимірюємо тривалість сортування масиву дати та часу
        long timeStart = System.nanoTime();

        Arrays.sort(dateTimeArray);
        arraySorted = true;

        PerformanceTracker.displayOperationTime(timeStart, "сортування масиву дати i часу");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    private void findInArray() {
        // відстежуємо час виконання пошуку в масиві
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

        // відстежуємо час на визначення граничних значень
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
     * Здійснює пошук конкретного значення в черзі дати та часу.
     */
    private void findInQueue() {
        // вимірюємо час пошуку в черзі
        long timeStart = System.nanoTime();

        boolean elementExists = this.dateTimeQueue.contains(dateTimeValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в Queue дати i часу");

        if (elementExists) {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' знайдено в Queue");
        } else {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' відсутній в Queue.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в черзі LocalDateTime.
     */
    private void locateMinMaxInQueue() {
        if (dateTimeQueue == null || dateTimeQueue.isEmpty()) {
            System.out.println("Черга є пустою або не ініціалізованою.");
            return;
        }

        // відстежуємо час пошуку граничних значень
        long timeStart = System.nanoTime();

        LocalDateTime minValue = Collections.min(dateTimeQueue);
        LocalDateTime maxValue = Collections.max(dateTimeQueue);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в Queue");

        System.out.println("Найменше значення в Queue: " + minValue);
        System.out.println("Найбільше значення в Queue: " + maxValue);
    }

    /**
     * Виконує операції peek і poll з чергою LocalDateTime.
     */
    private void performQueueOperations() {
        if (dateTimeQueue == null || dateTimeQueue.isEmpty()) {
            System.out.println("Черга є пустою або не ініціалізованою.");
            return;
        }

        LocalDateTime headElement = dateTimeQueue.peek();
        System.out.println("Головний елемент черги (peek): " + headElement);

        headElement = dateTimeQueue.poll();
        System.out.println("Видалений елемент черги (poll): " + headElement);

        headElement = dateTimeQueue.peek();
        System.out.println("Новий головний елемент черги: " + headElement);
    }


}

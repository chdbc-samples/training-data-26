import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Клас BasicDataOperationsUsingList реалізує операції з колекціями типу ArrayList для даних LocalDateTime.
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
public class BasicDataOperationsUsingList {
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
    BasicDataOperationsUsingList(LocalDateTime dateTimeValueToSearch, LocalDateTime[] dateTimeArray) {
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

        // зберігаємо до окремого файлу відсортований вміст ArrayList
        DataFileHandler.writeArrayToFile(dateTimeList.toArray(new LocalDateTime[0]), BasicDataOperations.SORTED_FILE_PREFIX + ".list.data");
    }

    /**
     * Упорядковує масив об'єктів LocalDateTime за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    void performArraySorting() {
        long timeStart = System.nanoTime();

        Arrays.sort(dateTimeArray);
        arraySorted = true;

        PerformanceTracker.displayOperationTime(timeStart, "сортування масиву LocalDateTime[]");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    void findInArray() {
        // назву операції будуємо до заміру, щоб склеювання рядка не потрапило у вимір
        String operationName = "пошук елемента '" + dateTimeValueToSearch + "' в масивi LocalDateTime[], " + (arraySorted ? "після сортування" : "до сортування");
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

        PerformanceTracker.displayOperationTime(timeStart, operationName);

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

        // назву операції будуємо до заміру, щоб склеювання рядка не потрапило у вимір
        String operationName = "визначення мiнiмальної i максимальної дати в масивi LocalDateTime[], " + (arraySorted ? "після сортування" : "до сортування");
        long timeStart = System.nanoTime();

        LocalDateTime minValue = dateTimeArray[0];
        LocalDateTime maxValue = dateTimeArray[0];

        if (arraySorted) {
            // у відсортованому масиві граничні значення лежать на його кінцях
            maxValue = dateTimeArray[dateTimeArray.length - 1];
        } else {
            for (LocalDateTime currentDateTime : dateTimeArray) {
                if (currentDateTime.isBefore(minValue)) {
                    minValue = currentDateTime;
                }
                if (currentDateTime.isAfter(maxValue)) {
                    maxValue = currentDateTime;
                }
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, operationName);

        System.out.println("Найменше значення в масивi: " + minValue);
        System.out.println("Найбільше значення в масивi: " + maxValue);
    }

    /**
     * Шукає конкретне значення дати та часу в колекції ArrayList.
     */
    void findInList() {
        // назву операції будуємо до заміру, щоб склеювання рядка не потрапило у вимір
        String operationName = "пошук елемента '" + dateTimeValueToSearch + "' в ArrayList<LocalDateTime>, " + (listSorted ? "після сортування" : "до сортування");
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

        PerformanceTracker.displayOperationTime(timeStart, operationName);

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

        // назву операції будуємо до заміру, щоб склеювання рядка не потрапило у вимір
        String operationName = "визначення мiнiмальної i максимальної дати в ArrayList<LocalDateTime>, " + (listSorted ? "після сортування" : "до сортування");
        long timeStart = System.nanoTime();

        LocalDateTime minValue;
        LocalDateTime maxValue;

        if (listSorted) {
            // у відсортованому списку граничні значення лежать на його кінцях
            minValue = dateTimeList.get(0);
            maxValue = dateTimeList.get(dateTimeList.size() - 1);
        } else {
            minValue = Collections.min(dateTimeList);
            maxValue = Collections.max(dateTimeList);
        }

        PerformanceTracker.displayOperationTime(timeStart, operationName);

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

        PerformanceTracker.displayOperationTime(timeStart, "сортування ArrayList<LocalDateTime>");
    }

    // BEGIN CONDITION
    /** вибірка даних; sorted=true лише для даних, упорядкованих за зростанням. */
    public int analyzeCondition(LocalDateTime[] values, boolean sorted, LocalDateTime lower, LocalDateTime upper) {
        if (lower.isAfter(upper)) throw new IllegalArgumentException("Нижня межа перевищує верхню");
        String conditionDescription = "значення в інтервалі [" + lower + "; " + upper + "] включно";
        String phase = sorted ? "після сортування" : "до сортування";
        System.out.println("Масив: вибірка даних " + phase + ". Умова: " + conditionDescription);
        // назву операції будуємо до заміру, щоб склеювання рядка не потрапило у вимір
        String operationName = "вибірка даних: масив LocalDateTime[], " + phase + ", умова: " + conditionDescription;
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
            // Етап 1. Бінарним пошуком знаходимо перший елемент, не менший за нижню межу.
            // Масив упорядкований, тому спочатку йдуть елементи, менші за lower, а після них решта.
            // Шукаємо індекс, де закінчується перша частина, і з кожним кроком
            // удвічі звужуємо проміжок [low; high), у якому цей індекс може бути.
            int low = 0;
            int high = values.length;
            while (low < high) {
                // середина проміжку; зсув >>> 1 ділить на 2 без переповнення int
                int mid = (low + high) >>> 1;
                inspected++;
                // values[mid] < lower: шуканий індекс лежить правіше mid, тож відкидаємо ліву половину разом із mid;
                // інакше values[mid] може бути шуканим, тож лишаємо mid і відкидаємо праву половину
                if (values[mid].isBefore(lower)) low = mid + 1; else high = mid;
            }
            // Тепер low == high: це перший елемент >= lower (або values.length, якщо таких немає).
            // Знадобилось близько log2(n) порівнянь замість перегляду всіх менших елементів.

            // Етап 2. Від нижньої межі йдемо вправо до першого елемента, більшого за верхню межу.
            // Усі елементи до нього вже >= lower, тому окремо перевіряти нижню межу не треба.
            for (int i = low; i < values.length; i++) {
                LocalDateTime value = values[i];
                inspected++;
                // далі всі елементи ще більші, тож решту масиву можна не переглядати
                if (value.isAfter(upper)) break;
                matches[count++] = value;
            }
        }
        PerformanceTracker.displayOperationTime(timeStart, operationName);
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
        // назву операції будуємо до заміру, щоб склеювання рядка не потрапило у вимір
        String operationName = "вибірка даних: ArrayList<LocalDateTime>, " + phase + ", умова: " + conditionDescription;
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
            // Етап 1. Бінарним пошуком знаходимо перший елемент, не менший за нижню межу.
            // Список упорядкований, тому спочатку йдуть елементи, менші за lower, а після них решта.
            // Шукаємо індекс, де закінчується перша частина, і з кожним кроком
            // удвічі звужуємо проміжок [low; high), у якому цей індекс може бути.
            // Доступ get(mid) швидкий (O(1)) лише для ArrayList; для LinkedList він повільний.
            int low = 0;
            int high = values.size();
            while (low < high) {
                // середина проміжку; зсув >>> 1 ділить на 2 без переповнення int
                int mid = (low + high) >>> 1;
                inspected++;
                // values.get(mid) < lower: шуканий індекс лежить правіше mid, тож відкидаємо ліву половину разом із mid;
                // інакше values.get(mid) може бути шуканим, тож лишаємо mid і відкидаємо праву половину
                if (values.get(mid).isBefore(lower)) low = mid + 1; else high = mid;
            }
            // Тепер low == high: це перший елемент >= lower (або values.size(), якщо таких немає).
            // Знадобилось близько log2(n) порівнянь замість перегляду всіх менших елементів.

            // Етап 2. Від нижньої межі йдемо вправо до першого елемента, більшого за верхню межу.
            // Усі елементи до нього вже >= lower, тому окремо перевіряти нижню межу не треба.
            java.util.ListIterator<LocalDateTime> cursor0 = values.listIterator(low);
            while (cursor0.hasNext()) {
                LocalDateTime value = cursor0.next();
                inspected++;
                // далі всі елементи ще більші, тож решту списку можна не переглядати
                if (value.isAfter(upper)) break;
                matches[count++] = value;
            }
        }
        PerformanceTracker.displayOperationTime(timeStart, operationName);
        // Друк результатів не входить у виміряний час пошуку.
        System.out.println("Переглянуто елементів: " + inspected);
        for (int i = 0; i < count; i++) System.out.println(matches[i]);
        System.out.println("У List знайдено " + count + " входжень (повтори враховано). Умова: " + conditionDescription + ".");
        return count;
    }
    // END CONDITION
}

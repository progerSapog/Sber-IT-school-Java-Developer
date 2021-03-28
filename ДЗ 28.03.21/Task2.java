import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Класс, содержащий точку входа в программу - метод main.
 * Язык: java
 *
 * Реализация домашнего задания Sber ITSchool от 28.03.21
 *
 * Текст задания:
 *  На ввод подается n чисел не разделенных пробелом (строка вида 123235094659843).
 *  Вычислить k-тый символ строки. Целочисленный параметр k передается
 *  пользователем.
 *
 *  Входные данные: <[min/max]> <1,2, ..., n>.
 *  Выходные данные: в зависимости от первого параметра min/max вывести в
 *  терминал минимальное/максимальное число.
 *
 * @release:     28.03.21
 * @last_update: 28.03.21
 *
 * @author Vladislav Sapozhnikov 19-IVT-3 (github: https://github.com/progerSapog )
 */
public class Task2
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите max/min для нахождения max/min элемента: ");
        String minMax = scanner.nextLine();

        System.out.print("Введите строку из n целых числе без пробела: ");
        String str = scanner.nextLine();

        int minMaxInt = 0;

        //Переводим строку в массив символов char
        char[] charArr = str.toCharArray();

        //Из массива типа char переводим в массив Integer
        Integer[]  intArr  = new Integer[charArr.length];
        for (int i = 0; i < intArr.length; i++)
        {
            intArr[i] = Integer.parseInt(String.valueOf(charArr[i]));
        }

        //Переводим массив int в список, чтобы воспользоваться
        //средствами стан дартной библиотеки Collections.max/min
        List<Integer> intList = Arrays.asList(intArr);
        switch (minMax)
        {
            case ("max") -> minMaxInt = Collections.max(intList);
            case ("min") -> minMaxInt = Collections.min(intList);
            default      -> System.err.println("переданы неверные параметры!");
        }

        System.out.println("Искомый элемент (max/min): " + minMaxInt);

        System.out.print("Введите K (номер элемента, который вы хотите найти): ");
        int k = scanner.nextInt();

        System.out.println("Искомый элемент: " + intArr[k - 1]);
        System.out.println("Завершение работы...");
    }
}

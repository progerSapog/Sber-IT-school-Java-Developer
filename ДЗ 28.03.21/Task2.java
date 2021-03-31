import java.util.Scanner;

/**
 * Класс, содержащий точку входа в программу - метод main.
 * Язык: java
 *
 * Реализация домашнего задания Sber ITSchool от 28.03.21
 *
 * Текст задания:
 *  Написать программу, выполняющую рисование рамки вокруг текстовой строки.
 *  Программа должна принимать на вход размеры рамки (длина/ширина) и саму строку.
 *
 *  Текстовая строка должна быть отцентрирована как по по горизонатли, так и по вертикали.
 *  В случае, если длина строки не позволяет вписать строку в рамку заданного размера,
 *  программа должна иметь рамки выводить сообщение.
 *
 * @release:     28.03.21
 * @last_update: 28.03.21
 *
 * @author Vladislav Sapozhnikov 19-IVT-3 (github: https://github.com/progerSapog )
 */
public class Task2
{
    /**
     * Метод, выводящий в консоль текст с рамкой заданных парамтров
     *
     * @param length - длина  рамки
     * @param width  - ширина рамки
     * @param str    - текст для вывода
     * */
    public static void printWordInFrame(int length, int width, String str)
    {
        //Получаем кол-во пробелов перед строкой
        int space1 = (width - str.length())/2;

        //Проход по каждой строке
        for (int i = 0; i <= length; i++)
        {
            //Если строка является первой или последней, то она состоит
            //полностью из "*"
            if (i == 0 || i == length)
            {
                for (int j = 0; j < width; j++)
                {
                    System.out.print("*");
                }
                System.out.println();
                continue;
            }

            //В остальны случаях выводим "*" + кол-во проеблов до строки + строку +
            //+ кол-во пробелов после строки + "*"
            System.out.print("*");
            for (int j = 0; j < space1; j++)
            {
                System.out.print(" ");
            }
            System.out.print(str);
            for (int j = space1 + str.length(); j < width - 2; j++)
            {
                System.out.print(" ");
            }
            System.out.println("*");
        }
    }

    public static void main(String[] args)
    {
        //Открытие потока ввода
        Scanner scanner = new Scanner(System.in);

        //Ввод длины
        System.out.print("Введите длину рамки: ");
        int length = scanner.nextInt();

        //Ввод ширины
        System.out.print("Введите ширину рамки: ");
        int width  = scanner.nextInt();

        //Сброс потока ввода
        scanner.nextLine();

        //Ввод строки
        System.out.print("Введите строку: ");
        String str = scanner.nextLine();

        //Если переданная строка не вмещается в рамку, то сообщаем об этом
        if (str.length() > width)
        {
            System.out.println("Ошибка!!! Ширина рамки меньше длины строки.");
            System.exit(1);
        }
        //Если переданны неверные параметры рамик, то сообщаем об этом
        if ((width < 2) || (length < 2))
        {
            System.out.println("Ошибка!!! Неверные параметры рамки!");
            System.exit(1);
        }

        //Печать строки с рамкой
        printWordInFrame(length, width, str);

        System.out.println("Завершение работы...");
    }
}

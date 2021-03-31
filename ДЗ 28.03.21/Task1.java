import java.util.Random;
import java.util.Scanner;

/**
 * Класс, содержащий точку входа в программу - метод main.
 * Язык: java
 *
 * Реализация домашнего задания Sber ITSchool от 28.03.21
 *
 * Текст задания:
 *  Сгенерировано случайное целочисленное число (любой диапазон).
 *  Необходимо написать программу, считывающую вводимое пользователем
 *  значение до тех пор, пока значение передаваемое в программу не совпадет
 *  со случайным значением. Если введенное число больше или меньше
 *  случайного, программа дает подсказку (больше/меньше).
 *
 * @release:     28.03.21
 * @last_update: 28.03.21
 *
 * @author Vladislav Sapozhnikov 19-IVT-3 (github: https://github.com/progerSapog )
 */
public class Task1
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        //Получение случаного целочисненного числа при
        //помощи класса Random
        int digit = random.nextInt(10_000);

//        System.out.println(digit);
        int inputInt;

        do
        {
            System.out.print("Введите число: ");
            inputInt = scanner.nextInt();

            if (inputInt < digit)
            {
                System.out.println("Введеное число меньше загаданного");
            }
            else if (inputInt > digit)
            {
                System.out.println("Введеное число больше загаданного");
            }
            else
            {
                System.out.println("Ты угадал число!!! Молодец!");
            }
        }
        while (inputInt != digit);

        System.out.println("Завершение работы...");
    }
}
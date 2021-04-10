import java.util.*;

/**
 * Класс, содержащий точку входа в программу - метод main.
 * Язык: java
 *
 * Реализация домашнего задания Sber ITSchool от 04.04.21
 *
 * Текст задания:
 *   Реализовать собственный ArrayList с использованием Genericов, в нем должны быть реализованы все методы ниже:
 *          add()       getSize()
 *          get()       sort()
 *          set()       equals()
 *          delete()    iterator()
 *          toArray()
 *
 * @release:     03.04.21
 * @last_update: 03.04.21
 *
 * @author Vladislav Sapozhnikov (github: https://github.com/progerSapog )
 */
public class Main
{
    public static void main(String[] args)
    {
        Random random = new Random();
        MyArrayList<Integer> myArrayList1 = new MyArrayList<>();

        System.out.println("Заполнение списка при помощи метода add()");
        for (int i = 0; i < 25; i++)
        {
            myArrayList1.add(random.nextInt(100));
        }
        System.out.println("myArrayList1: " + myArrayList1);
        System.out.println("Добавление через add по индексу: индекс 3 элемент 10000000");
        myArrayList1.add(3,10000000);
        System.out.println(myArrayList1);

        System.out.println();
        System.out.println("Создании списка на основе другой коллекции: ");
        Set<String> set = new LinkedHashSet<>();
        set.add("Идут");
        set.add("неуклюже");
        set.add("пешеходы");
        set.add("по");
        set.add("лужам");

        MyArrayList<String> myArrayList2 = new MyArrayList<>(set);

        System.out.println("LinkedHashSet: " + set);
        System.out.println("MyArrayList на основе LinkedHashSet: " + myArrayList2);

        System.out.println();
        System.out.println("myArrayList1: " + myArrayList1);
        System.out.println("Получение 5ого элемента при помощи get(): " + myArrayList1.get(5));
        myArrayList1.set(5, 523656);
        System.out.println("Замена 5ого элемента при помощи set(523656): " + myArrayList1);
        System.out.println();

        System.out.println("Удаление 5ого элемента при помощи метода delete()");
        System.out.println("Размер до удаления: " + myArrayList1.getSize());
        myArrayList1.delete(5);
        System.out.println("Размер после удаления: " + myArrayList1.getSize());
        System.out.println("myArrayList1: " + myArrayList1);
        System.out.println();

        System.out.println("Сортировка - sort(Comparator): ");
        myArrayList1.sort((integer, t1) -> {
            if (integer.equals(t1)) return 0;
            if (integer > t1) return 1;
            else return -1;
        });
        for (Integer el: myArrayList1)
        {
            System.out.print(el + " ");
        }
        System.out.println();

        System.out.println("Обход итератором: ");
        Iterator<Integer> iterator = myArrayList1.iterator();
        while (iterator.hasNext()) System.out.print(iterator.next() + " ");

        System.out.println("\n\tЗавершение работы...");
    }
}

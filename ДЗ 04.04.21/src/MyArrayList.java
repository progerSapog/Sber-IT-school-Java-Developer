import java.util.*;

/**
 * Реализация собственной коллекции ArrayList (ДЗ Sber IT school от 04.04.2021).
 * Данный класс реализует методы:
 *  add()       getSize()
 *  get()       sort()
 *  set()       equals()
 *  delete()    iterator()
 *  toArray()
 * Так же реализует интерфейс Iterable
 * @see java.lang.Iterable
 *
 * @param <T> - тип данных, содержащихся в коллекции
 *
 * @release:     09.04.21
 * @last_update: 09.04.21
 *
 * @author Vladislav Sapozhnikov 19-IVT-3 (github: https://github.com/progerSapog )
 * */
public class MyArrayList<T> implements Iterable<T>
{
    private int capacity = 10;    //Вместительность массива
    private int size     = 0;     //Текущий размер массива (кол-во элементов в массиве)
    private int lastMod  = 0;     //Переменная для хранения индекса последнего элемента

    private Object[] data;        //массив данных

    /**
     * Вспомогательный метод проверки индекса.
     *
     * @param index - индекс для проверки.
     * @throws IndexOutOfBoundsException - исключение, выбрасываемое если переданный индекс
     *                                     выходит за переделы списка.
     * */
    private void checkIndex(int index)
    {
        if (index > size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    /**
     * Вспомогательный метод расширения вместимости списка.
     * */
    private void increaseCapacity()
    {
        //Увеличиваем вместимость в 2 раза
        this.capacity = capacity << 1;

        //Создаем новый массив увеличенной вместимости,
        //копируем в него имеющиеся элементы
        //присваиваем полю data значение новосозданного массива
        Object[] tempArr = this.data;
        this.data = new Object[capacity];

        System.arraycopy(tempArr, 0, this.data, 0, tempArr.length);
    }

    /**
     * Конструктор с параметром.
     *
     * @param capacity - начальная вместительность списка.
     * */
    public MyArrayList(int capacity)
    {
        if (capacity <= 0) throw new IllegalArgumentException("Illegal capacity: " + capacity);

        this.capacity = capacity;
        this.data = new Object[capacity];
    }

    /**
     * Конструктор без параметров. Создается список
     * с вместительность по умолчанию (10 элементов);
     * */
    public MyArrayList()
    {
        this.data = new Object[capacity];
    }

    /**
     * Конструтор с параметром. Позволяет создать список
     * на основе переданной коллекции.
     *
     * @param c - коллекция, на основе которой необходимо передать список.
     * */
    public MyArrayList(Collection<? extends T> c)
    {
        Object[] tempArr = c.toArray();

        this.size = tempArr.length;
        this.data = Arrays.copyOf(tempArr, this.size, Object[].class);
    }

    /**
     * Метод для добавления элемента в конец списка.
     *
     * @param element - элемент который необходимо вставить.
     * */
    public void add(T element)
    {
        //Увеличиваем индекс последнего элемента
        ++this.lastMod;

        //Если индекс вышел за вместительность, то увеличиваем вместительность
        if (lastMod > capacity) increaseCapacity();

        //Вставка элемента в конец и увеличение счетчика размера списка
        this.data[size] = element;
        size++;
    }

    /**
     * Метод для добавления элемента по индексу.
     *
     * @param element - элемент который необходимо добавить
     * @param index   - индекс, по которому необходимо добавить элемент
     * */
    public void add(int index, T element)
    {
        //Проверка индекса
        checkIndex(index);

        //Увеличиваем индекс последнего элемента и проверяем вместительность
        ++this.lastMod;
        if (lastMod > capacity) increaseCapacity();

        //Создаем временный массив, в него копируем элементы до индекса вставки
        Object[] tempArr = new Object[capacity];
        System.arraycopy(this.data, 0, tempArr, 0, index);

        //Вставляем новый элемент по индексу
        tempArr[index] = element;

        //Смещаем остальные элементы если таковые остались
        if (data.length - index >= 0) System.arraycopy(data, index, tempArr, index + 1, data.length - index - 1);

        //Присваиваем полю data новый массив и увеличваем считчик размерности.
        this.data = tempArr;
        size++;
    }

    /**
     * Методя для получения элемента по индексу.
     *
     * @param index - индекс элемента, который необходио вернуть
     * @return - элемент списка по заданному индексу
     * */
    public T get (int index)
    {
        checkIndex(index);
        return (T) this.data[index];
    }

    /**
     * Метод для установки элемента по заданному индексу.
     *
     * @param index   - индекс элемента для замены
     * @param element - элемент, на который производится замена
     * */
    public void set(int index, T element)
    {
        checkIndex(index);
        this.data[index] = element;
    }

    /**
     * Метод для удаления элемента по заданному индексу.
     * */
    public void delete(int index)
    {
        //проверяем индекс
        checkIndex(index);

        //Уменьашем индекс конечного элемента
        --this.lastMod;

        //Создаем временный массив, в него копируем все значения до заданного индекса
        Object[] tempArr = new Object[capacity];
        System.arraycopy(this.data, 0, tempArr, 0, index);

        //Копируем оставшиеся элемента, после элемента который необходимо удалить
        System.arraycopy(data, index + 1, tempArr, index, data.length - index - 1);

        //Присваиваем полю data значение новго массива и уменьшаем размерность.
        this.data = tempArr;
        size--;
    }

    /**
     * Метод проверки равенства двух объектов данного класса.
     *
     * @return true, если элементы равны
     * */
    public boolean equals(MyArrayList<T> anotherList)
    {
        //Элементы не равны если не равны их размеры
        if (this.size != anotherList.size) return false;

        //Объекты не равны, если не равны хотя бы одни их элементы
        for (int i = 0; i < this.size; i++)
        {
            if (this.data[i] != anotherList.data[i]) return false;
        }

        //В остальных случаях объекты равны
        return true;
    }

    /**
     * Метод сортировки по заданному объекту Comparator'у
     *
     * @param c - объект, реализующий интерфейс Comparator. Задает
     *            правила сортировки коллекции.
     * */
    public void sort(Comparator<? super T> c)
    {
        Arrays.sort((T[]) this.data, 0, this.size, c);
    }

    /**
     * Метод позволяющий получить массив из объекта данного класса.
     *
     * @return - массив элементов, содержащихся в списке.
     * */
    public Object[] toArray()
    {
        return Arrays.copyOf(this.data, this.size);
    }

    /**
     * Метод для получения размерности текущего списка.
     *
     * @return размерность списка
     * */
    public int getSize()
    {
        return size;
    }

    /**
     * Метод, отвечающий за отображение объектов данного класса
     * в потоке вывода.
     *
     * @return строковое представление элемента
     * */
    @Override
    public String toString()
    {
        //Данная нетривиальная конструкция позволяет вернуть
        //массив без элементов null
        return Arrays.toString(Arrays.copyOf(this.data, this.size));
    }

    /**
     * Метод для получения итератора данной коллекции.
     *
     * @return итератор данной колекции
     * */
    @Override
    public Iterator<T> iterator()
    {
        return new MyArrayListIterator();
    }

    /**
     * Вложенный класс итератор для данной коллекции.
     * Данный итератор является одноправленным для прохода вперед.
     *
     * @see java.util.Iterator
     *
     * @release:     09.04.21
     * @last_update: 09.04.21
     * */
    private class MyArrayListIterator implements Iterator<T>
    {
        private int currPos;    //Переменная для хранения текущего положения итетора

        /**
         * Конструктор без параметров.
         * Начальная позиция - перед нулевым элементом коллекции.
         * */
        public MyArrayListIterator()
        {
            this.currPos = -1;
        }

        /**
         * Метод проверки наличия следущего элемента.
         *
         * @return true если есть след. элемент
         * */
        @Override
        public boolean hasNext()
        {
            //Возвращает false если выход за пределы коллекции или след элемент null
            if (currPos + 1 >= size) return false;
            return data[currPos + 1] != null;
        }

        /**
         * Получение следущего элемента колекции при помощи итератора.
         *
         * @return - след. элемент коллекции.
         * */
        @Override
        public T next()
        {
            if (!this.hasNext())
            {
                throw new NoSuchElementException();
            }

            return (T) data[++currPos];
        }

        /**
         * Заглушка. Метод не реализован.
         * */
        @Override
        public void remove() {
            return;
        }
    }
}

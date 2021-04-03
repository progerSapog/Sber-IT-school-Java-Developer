package com.company;

/* Указываем тип, с которым может сравниваться класс,
 * реализующий Comparable.
 * Получается, что при сортировке объекты Book могут
 * сравниваться с другими объектами Book. */
public class Book implements Comparable<Book> {
    private String title;
    private String author;
    private int year;
    private int pages;

    Book(String title, String author, int year, int pages) {
        this.year = year;
        this.pages = pages;
        this.author = author;
        this.title = title;
    }

    /* Метод sort передает объект Book в compareTo(), чтобы
     * увидеть, как тот соотносится с экземпляром Book,
     * из которого вызван метод. */
    @Override
    public int compareTo(Book otherBook) {
        /* У string есть метод compareTo, поэтому
         * перекладываем всю работу на него. */
        return this.title.compareTo(otherBook.getTitle());
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return pages;
    }

    public int getYear() {
        return year;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        return title;
    }

    /**
     * Хэш-функция основанная на делении.
     * Хеш-код вычисляется как сумма остатоков от деления на число
     * всех возможных хешей.
     * Для уменьшении числа коллизий принято брать простое число для деления.
     *
     * @return - хеш-код объекта.
     *
     * @author Vladislav Sapozhnickov (github: https://github.com/progerSapog)
     * */
    @Override
    public int hashCode()
    {
        int divider = 23;    //Делитель - простое число
        int hashCode = 0;    //Переменная для хранения хэш-кода

        //От каждого символа строки title  получаем остаток от деления
        //и добавляем его к значению хэша
        for (int i = 0; i < title.length(); i++)
        {
            hashCode += title.charAt(i) % divider;
        }

        //От каждого символа строки author получаем остаток от деления
        //и добавляем его к значению хэша
        for (int i = 0; i < author.length(); i++)
        {
            hashCode += author.charAt(i) % divider;
        }

        //Добавляем к хэшу остатки от деления полей year и pages
        hashCode += year  % divider;
        hashCode += pages % divider;

        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Book) {
            Book book = (Book) obj;
            return this.author.equals(book.author) &&
                    this.title.equals(book.title) &&
                    this.pages == book.pages &&
                    this.year == book.year;
        }
        return false;
    }
}

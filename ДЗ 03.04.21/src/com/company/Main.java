package com.company;

/**
 * Класс, содержащий точку входа в программу - метод main.
 * Язык: java
 *
 * Реализация домашнего задания Sber ITSchool от 03.04.21
 *
 * Текст задания:
 *   Реализовать свою функцию hashCode() для класса Book
 *
 * @release:     03.04.21
 * @last_update: 03.04.21
 *
 * @author Vladislav Sapozhnikov 19-IVT-3 (github: https://github.com/progerSapog )
 */
public class Main
{
    public static void main(String[] args)
    {
        Book book1 = new Book("The great Gatsby", "Fitzgerald", 1999, 2000);
        Book book2 = new Book("The great Gatsby", "Fitzgerald", 1999, 2000);
        Book book3 = new Book("Animal farm", "Orwell", 19992, 20002);
        Book book4 = new Book("Animal farm", "Orwell", 2001, 629);
        Book book5 = new Book("1984", "Orwell", 1979, 1340);

        System.out.println("book1 hashcode: " + book1.hashCode());
        System.out.println("book2 hashcode: " + book2.hashCode());
        System.out.println("book3 hashcode: " + book3.hashCode());
        System.out.println("book4 hashcode: " + book4.hashCode());
        System.out.println("book5 hashcode: " + book5.hashCode());
    }
}
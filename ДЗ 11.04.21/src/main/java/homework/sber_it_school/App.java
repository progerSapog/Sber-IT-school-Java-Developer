package homework.sber_it_school;

import homework.sber_it_school.events.DoneTask;
import homework.sber_it_school.events.SolveTask;
import homework.sber_it_school.events.WriteMessage;
import homework.sber_it_school.log.Status;
import homework.sber_it_school.log_parser.LogParser;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;

/**
 * Класс, содержащий точку входа в программу - метод main.
 * Язык: java
 *
 * Реализация домашнего задания Sber ITSchool от 11.04.21
 *
 * Текст задания:
 *   Реализовать Парсер Логов
 *
 * @release:     14.04.21
 * @last_update: 14.04.21
 *
 * @author Vladislav Sapozhnikov (github: https://github.com/progerSapog )
 */
public class App
{
    public static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");

    public static void main(String[] args)
    {
        LOGGER.info("Старт работы.");

        //Путь к файлу/директории
        Path path = Paths.get("/home/progersapog/Документы/sber_homework/logs_to_read/");

        //Создание объекта LogParse и передаче пути в конструтор
        LogParser logParser = new LogParser(path);

        //Чтение логов из файла
        try
        {
            logParser.readLogs();
        }
        catch (IOException exception)
        {
            LOGGER.error("Ошибка при открытии файла: " + path.getFileName());
            System.exit(-1);
        }

        //При помощи общего метода для выписывания студентов по событию и статусу
        //выписываем студентов, у которых есть выполненеы задания (Согласно основному заданию)
        logParser.outputUsernamesByKeys("students_with_done_task.txt", new DoneTask(), Status.OK);

        Date before = new Date((2013 - 1900), (12-1), 12, 21, 56, 30);
        Date after  = new Date((2016 - 1900), (3-1), 19, 0, 0, 0);

        //Демонстрация методов интерфейса
        System.out.println("Фильтрация с " + dateFormat.format(before) + " до " + dateFormat.format(after));
        System.out.println("Кол-во уникальных IP: " + logParser.getNumberOfUniqueIPs(before, after));
        System.out.println("Уникальные IP: " + logParser.getUniqueIPs(before, after));
        System.out.println("Уникальные с которых заходил Vasya Pupkin: " + logParser.getIPsForUser("Vasya Pupkin", before, after));
        System.out.println("Уникальные IP с которых отправлялись сообщения: " + logParser.getIPsForEvent(new WriteMessage(), before, after));
        System.out.println("Уникальные IP где встречались события со статусом ERROR: " + logParser.getIPsForStatus(Status.ERROR,  before, after));

        LOGGER.info("Завершение работы.");
    }
}

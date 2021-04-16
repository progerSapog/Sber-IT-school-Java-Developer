package homework.sber_it_school.log_parser;

import homework.sber_it_school.events.*;
import homework.sber_it_school.log.Log;
import homework.sber_it_school.log.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Парсер логов.
 * Умеет читать и обрабатывать логи из файлов расширения .log
 * Путь к файлу указывается указывается через перемнную объекта Path
 * Если найдено несколько файлов с расширением .log будут прочитаны и записаны все файлы.
 *
 * @see Log
 * */
public class LogParser implements IPQuery
{
    public static final Logger LOGGER = LoggerFactory.getLogger(LogParser.class);

    //Регулярное выражение для выдыления IP из считанной строки
    private final Pattern ipPattern     = Pattern.compile("\\d{2,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");

    //Регулярное выражение для выдыления username из считанной строки
    private final Pattern namePattern   = Pattern.compile("[A-z]+\\s*[A-z]*\\s*[A-z]*");

    //Регулярное выражение для выдыления даты(дд.мм.гггг) из считанной строки
    private final Pattern datePattern   = Pattern.compile("\\d{1,2}\\.\\d{1,2}\\.\\d{4}");

    //Регулярное выражение для выдыления времени(чч:мм:сс) из считанной строки
    private final Pattern timePattern   = Pattern.compile("\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}");

    //Регулярное выражение для выдыления события из считанной строки
    private final Pattern eventPattern  = Pattern.compile("[A-Z]{2,}_*[A-Z]*\\s*\\d*");

    //Регулярное выражение для выдыления статуса из считанной строки
    private final Pattern statusPattern = Pattern.compile("[A-Z]{1,6}$");


    private Matcher matcher;      //Объект класса, реализующего поиск по преденнаому шаблону(объект Pattern)
    private Path logDir;          //Переменная для хранения пути к файлу/директории

    private List<Log> logList;    //список считанных логов

    /**
     * Вспомогательный метод для выдедения IP из считанной строки
     *
     * @param  line - строка, из которой необходимо выделить IP
     * @return строка с IP
     * */
    private String parseIPFromStr(String line)
    {
        matcher = ipPattern.matcher(line);
        if (matcher.find()) return line.substring(matcher.start(), matcher.end());
        return null;
    }

    /**
     * Вспомогательный метод для выдедения username из считанной строки
     *
     * @param  line - строка, из которой необходимо выделить IP
     * @return строка с username
     * */
    private String parseNameFromStr(String line)
    {
        matcher = namePattern.matcher(line);
        if (matcher.find()) return line.substring(matcher.start(), matcher.end()).replace("\t", "");
        return null;
    }

    /**
     * Вспомогательный метод для выдедения даты из считанной строки
     *
     * @param  line - строка, из которой необходимо выделить дату
     * @return объект типа Date
     * */
    private Date parseDateFromStr(String line)
    {
        //временный список строк для хранения полученных после разделения строк
        List<String> timeDataList = new ArrayList<>();

        matcher = datePattern.matcher(line);
        if (matcher.find())
        {
            //Полученную строку вида дд.мм.гггг делим по точке(.) и заносим в промежуточный массив
            String[] strArr = line.substring(matcher.start(), matcher.end()).split("\\.");

            //Значения массива помещаем в список, общий для хранения даты
            timeDataList.addAll(Arrays.asList(strArr));
        }

        matcher = timePattern.matcher(line);
        if (matcher.find())
        {
            //Полученную строку вида чч:мм:сс делим по двоеточию(:) и заносим в промежуточный массив
            String[] strArr = line.substring(matcher.start(), matcher.end()).split("\\:");

            //Значения массива помещаем в список, общий для хранения даты
            timeDataList.addAll(Arrays.asList(strArr));
        }

        //Значения даты и времени в виде строк переписываем в виде int'ов
        List<Integer> tempIntList = new ArrayList<>();
        for (String s : timeDataList)
        {
            tempIntList.add(Integer.parseInt(s));
        }

        //Из полученного списка: [дд, мм, гггг, чч, мм, cc] создаем объект типа Date
        //Значение года данного объекта высчитывается как 1970 + переданное значение, поэтому изначального значения
        //необходимо вычесть 1970
        //Отсчет месяцев для данного типа данных начинается с 0, поэтому из передаваемого номера месяца
        //необходимо вычесть 1
        return new Date((tempIntList.get(2) - 1900), (tempIntList.get(1) - 1), tempIntList.get(0), tempIntList.get(3), tempIntList.get(4), tempIntList.get(5));
    }

    /**
     * Вспомогательный метод для выдедения события из считанной строки
     *
     * @param  line - строка, из которой необходимо выделить дату
     * @return объект типа Event
     * */
    private Event parseEventFromStr(String line)
    {
        matcher = eventPattern.matcher(line);
        if (matcher.find())
        {
            //Полученную строку делим по пробелам (потому что некоторые события могу содержать доп. параметры)
            String[] tempStr = line.substring(matcher.start(), matcher.end()).split("\\s");

            //Выбор по первому элементу
            switch (tempStr[0])
            {
                case "LOGIN":
                    return new Login();
                case "DONE_TASK":
                    //для события DONE_TASK так же записываем номер
                    return new DoneTask(Integer.parseInt(tempStr[1]));
                case "DOWNLOAD_PLUGIN":
                    return new DownloadPlugin();
                case "WRITE_MESSAGE":
                    return new WriteMessage();
                case "SOLVE_TASK":
                    //для события SOLVE_TASK так же записываем номер
                    return new SolveTask(Integer.parseInt(tempStr[1]));
            }
        }

        return null;
    }

    /**
     * Вспомогательный метод для выдедения статуса из считанной строки
     *
     * @param  line - строка, из которой необходимо выделить дату
     * @return объект типа Status
     * */
    private Status parseStatusFromStr(String line)
    {
        matcher = statusPattern.matcher(line);
        if (matcher.find())
        {
            switch (line.substring(matcher.start(), matcher.end()).replace("\t", ""))
            {
                case "OK":     return Status.OK;
                case "ERROR":  return Status.ERROR;
                case "FAILED": return Status.FAILED;
            }
        }

        return null;
    }

    /**
     * Вспомогательный метод, объединяющий все "подпарсеры"
     *
     * @param  line - строка, из которой необходимо выделить Log
     * @return объект типа Log
     * */
    private Log parseLogFromStr(String line)
    {
        Log tempLog = new Log();
        tempLog.setIP(parseIPFromStr(line));
        tempLog.setUsername(parseNameFromStr(line));
        tempLog.setDate(parseDateFromStr(line));
        tempLog.setEvent(parseEventFromStr(line));
        tempLog.setStatus(parseStatusFromStr(line));

        return tempLog;
    }

    /**
     * Вспомогательный метод для фильтрации логов по дате.
     * Если параметр after равен null, то нужно обработать все записи, у которых дата меньше или равна before.
     * Если параметр before равен null, то нужно обработать все записи, у которых дата больше или равна after.
     * Если и after, и before равны null, то нужно обработать абсолютно все записи (без фильтрации по дате).
     *
     * @param after  - объект типа Date - с  какой даты включительно осуществлять поиск
     * @param before - объект типа Date - до какой даты включительно осуществлять поиск
     * @return список отфильтрованных логов
     * */
    private List<Log> getLogsByDate(Date after, Date before)
    {
        List<Log> tempLogList = new ArrayList<>();

        //Заносим в список логи от after и до конца
        if (after != null && before == null)
        {
            for (Log log : logList)
            {
                if (log.getDate().after(after) || log.getDate().equals(after)) tempLogList.add(log);
            }
        }
        //Заносим в список логи от начала и до before
        else if (after == null && before != null)
        {
            for (Log log : logList)
            {
                if (log.getDate().before(before) || log.getDate().equals(before)) tempLogList.add(log);
            }
        }
        //Заносим в список все логи между before и after
        else if (after != null && before != null)
        {
            for (Log log : logList)
            {
                if ((log.getDate().after(after) || log.getDate().equals(after)) && (log.getDate().before(before) || log.getDate().equals(before))) tempLogList.add(log);
            }
        }
        //Иначе передаем все логи
        else tempLogList = logList;

        return tempLogList;
    }

    /**
     * Метод для чтения всех логов по переданному пути.
     * */
    public void readLogs() throws IOException
    {
        LOGGER.debug("Попытка прочтение файла.");

        //Получаем список файлов по переданному пути
        List<Path> pathsList = Files.walk(logDir).filter(Files::isRegularFile).collect(Collectors.toList());

        //Проход по каждому файлу
        for (Path path : pathsList)
        {
            String fileName = path.toString().replaceAll("\\s", "");

            //Если имя файла содержит расширения .log, то производим чтение логов
            if (fileName.contains(".log"))
            {
                //Открываем поток чтения
                try (BufferedReader reader = new BufferedReader(new FileReader(fileName), 512))
                {
                    String line;

                    //Проход по каждому строчке файла
                    while ((line = reader.readLine()) != null)
                    {
                        //Получаем из переданной строки объект типа Log
                        //и заносим его в список
                        logList.add(parseLogFromStr(line));
                    }

                    LOGGER.debug("Удачное прочтение файла.");
                }
            }
        }
    }

    /**
     * Метод записывающий в отдельный файл все имена студентов совершивших действие
     * с указанным событием и указанным статусом.
     * Если не найдены логи с заданным именем и статусом, то файл не создается.
     * @param fileName  - путь к файлу, в который необходимо записать логи
     * @param event      - тип события, логи с которым должны быть записаны
     * @param status     - статус, логи с которым должны быть записаны  */
    public void outputUsernamesByKeys(String fileName, Event event, Status status)
    {
        //Во временный список выписываем все логи подлежащие записи
        List<Log> tempList = new ArrayList<>();
        for (Log log : logList)
        {
            if ((log.getEvent().getClass() == event.getClass()) && log.getStatus().equals(status))
            {
                tempList.add(log);
            }
        }

        //Во временное мн-во строк пеерписываем имена пользователей из логов
        //чтобы исключить повторения
        Set<String> tempNameSet = new HashSet<>();
        for (Log log : tempList)
        {
            tempNameSet.add(log.getUsername() + "\n");
        }

        //Если размер временнего списка отличен от нуля (есть логи для записи)
        if (tempList.size() != 0)
        {
            LOGGER.debug("Создание файла: " + fileName);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
            {
                for (String name : tempNameSet)
                {
                    writer.write(name);
                }

                writer.flush();
            }
            catch (IOException exception)
            {
                LOGGER.error("Ошибка при создании файла: " + fileName);
            }
        }
    }

    /**
     * Метод для подсчета уникальных уникальных IP за заданный период
     *
     * @see IPQuery
     * @param after  - объект типа Date - с  какой даты включительно осуществлять поиск
     * @param before - объект типа Date - до какой даты включительно осуществлять поиск
     * @return       - кол-во уникальных IP за данный период
     * */
    @Override
    public int getNumberOfUniqueIPs(Date after, Date before)
    {
        Set<String> ipSet = new HashSet<>();
        List<Log> tempLog = getLogsByDate(after, before);

        for (Log log : tempLog)
        {
            ipSet.add(log.getIP());
        }

        return ipSet.size();
    }

    /**
     * Метод для получения мн-ва уникальных уникальных IP за заданный период
     *
     * @see IPQuery
     * @param after  - объект типа Date - с  какой даты включительно осуществлять поиск
     * @param before - объект типа Date - до какой даты включительно осуществлять поиск
     * @return       - мн-во уникальных IP за данный период
     * */
    @Override
    public Set<String> getUniqueIPs(Date after, Date before)
    {
        Set<String> ipSet = new HashSet<>();
        List<Log> tempLog = getLogsByDate(after, before);

        for (Log log : tempLog)
        {
            ipSet.add(log.getIP());
        }

        return ipSet;
    }

    /**
     * Метод для получения мн-ва уникальных уникальных IP с
     * которых работал пользователь за данный период.
     *
     * @see IPQuery
     * @param user   - имя пользователя
     * @param after  - объект типа Date - с  какой даты включительно осуществлять поиск
     * @param before - объект типа Date - до какой даты включительно осуществлять поиск
     * @return       - мн-во уникальных IP за данный период
     * */
    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before)
    {
        Set<String> ipSet = new HashSet<>();
        List<Log> tempLog = getLogsByDate(after, before);

        for (Log log : tempLog)
        {
            if (log.getUsername().equals(user)) ipSet.add(log.getIP());
        }

        return ipSet;
    }

    /**
     * Метод для получения мн-ва уникальных уникальных IP с
     * которых было воиспроизведено заданное событие.
     *
     * @see IPQuery
     * @param event  - событие, по которому ведется поиск
     * @param after  - объект типа Date - с  какой даты включительно осуществлять поиск
     * @param before - объект типа Date - до какой даты включительно осуществлять поиск
     * @return       - мн-во уникальных IP за данный период
     * */
    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before)
    {
        Set<String> ipSet = new HashSet<>();
        List<Log> tempLog = getLogsByDate(after, before);

        for (Log log : tempLog)
        {
            //Не equals() не instanceof не работают в данной ситации,
            //так что переходим к сравнению Class
            if (log.getEvent().getClass() == event.getClass())
               ipSet.add(log.getIP());
        }

        return ipSet;
    }

    /**
     * Метод для получения мн-ва уникальных уникальных IP с
     * которых было воиспроизведено событие с заданным статусом.
     *
     * @see IPQuery
     * @param status - статус события, по которому ведется поиск
     * @param after  - объект типа Date - с  какой даты включительно осуществлять поиск
     * @param before - объект типа Date - до какой даты включительно осуществлять поиск
     * @return       - мн-во уникальных IP за данный период
     * */
    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before)
    {
        Set<String> ipSet = new HashSet<>();
        List<Log> tempLog = getLogsByDate(after, before);

        for (Log log : tempLog)
        {
            //Не equals() не instanceof не работают в данной ситации,
            //так что переходим к сравнению Class
            if (log.getStatus().equals(status)) ipSet.add(log.getIP());
        }

        return ipSet;
    }

    /**
     * Конструктор, принимающий путь к файлу/директории
     *
     * @param logDir - объект типа Path, содержащий путь к файлу/директории
     * */
    public LogParser(Path logDir)
    {
        logList = new ArrayList<>();
        this.logDir = logDir;
    }

    /**
     * Геттер поля logDir
     *
     * @return значение поля logDir
     * */
    public Path getLogDir()
    {
        return logDir;
    }

    /**
     * Геттер поля logList
     *
     * @return значение поля logList
     * */
    public List<Log> getLogList()
    {
        return logList;
    }

    /**
     * Сеттер для поля logDir
     *
     * @param logDir - значение, которое необходимо установить полю logDir
     * */
    public void setLogDir(Path logDir)
    {
        this.logDir = logDir;
    }

    /**
     * Метод для вывода всех логов в консоль
     * */
    public void printAllLogs()
    {
        for (Log log : logList)
        {
            System.out.println(log);
        }
    }
}

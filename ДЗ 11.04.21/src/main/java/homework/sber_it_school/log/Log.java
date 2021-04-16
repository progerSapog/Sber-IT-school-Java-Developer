package homework.sber_it_school.log;

import homework.sber_it_school.events.Event;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс Log.
 *
 * @see homework.sber_it_school.log_parser.LogParser
 * @see Status
 * */
public class Log
{
    private String        IP;          //поле для хранения IP
    private String        username;    //поле для хранения имеин пользователя
    private Date          date;        //поле для хранения даты лога
    private Event         event;       //поле для хранения события
    private Status        status;      //поле для хранения статуса

    //Вспомогательное поле, опередляющее формат вывода объектов типа Date
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");

    /**
     * Геттер поля IP
     *
     * @return значение поля IP
     * */
    public String getIP()
    {
        return IP;
    }

    /**
     * Геттер поля username
     *
     * @return значение поля username
     * */
    public String getUsername()
    {
        return username;
    }

    /**
     * Геттер поля date
     *
     * @return значение поля date
     * */
    public Date getDate()
    {
        return date;
    }

    /**
     * Геттер поля event
     *
     * @return значение поля event
     * */
    public Event getEvent()
    {
        return event;
    }

    /**
     * Геттер поля status
     *
     * @return значение поля status
     * */
    public Status getStatus()
    {
        return status;
    }

    /**
     * Сеттер поля IP
     *
     * @param IP - значение, которое необходимо просвоить полю IP
     * */
    public void setIP(String IP)
    {
        this.IP = IP;
    }

    /**
     * Сеттер поля username
     *
     * @param username - значение, которое необходимо просвоить полю IP
     * */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Сеттер поля date
     *
     * @param date - значение, которое необходимо просвоить полю IP
     * */
    public void setDate(Date date)
    {
        this.date = date;
    }

    /**
     * Сеттер поля event
     *
     * @param event - значение, которое необходимо просвоить полю IP
     * */
    public void setEvent(Event event)
    {
        this.event = event;
    }

    /**
     * Сеттер поля status
     *
     * @param status - значение, которое необходимо просвоить полю IP
     * */
    public void setStatus(Status status)
    {
        this.status = status;
    }

    /**
     * Констурктор, принимающий значения всех полей.
     *
     * @param IP       - значение, которое необходимо просвоить полю IP
     * @param date     - значение, которое необходимо просвоить полю IP
     * @param event    - значение, которое необходимо просвоить полю IP
     * @param status   - значение, которое необходимо просвоить полю IP
     * @param username - значение, которое необходимо просвоить полю IP
     * */
    public Log(String IP, String username, Date date, Event event, Status status)
    {
        this.IP       = IP;
        this.username = username;
        this.date     = date;
        this.event    = event;
        this.status   = status;
    }

    public Log()
    {
    }

    /**
     * Перегруженный метод класса Object, отвечающий за строковое
     * представление объекта.
     *
     * @return строковое представление объекта.
     * */
    @Override
    public String toString()
    {
        return IP + " " + username + " " + dateFormat.format(date) + " " + event.toString() + " " + status;
    }
}

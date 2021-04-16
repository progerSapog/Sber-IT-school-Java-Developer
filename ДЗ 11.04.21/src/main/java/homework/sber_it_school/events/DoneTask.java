package homework.sber_it_school.events;

/**
 * Класс событие(event) - DONE_TASK.
 * Имеет доп. параметр - номер задачи.
 *
 * @see homework.sber_it_school.events.Event
 * */
public class DoneTask implements Event
{
    private Integer number;    //поля для хранения номера задачи

    public DoneTask() {
    }

    /**
     * Конструктор, принимающий номер задачи.
     *
     * @param number - номер задачи
     * */
    public DoneTask(Integer number)
    {
        this.number = number;
    }

    /**
     * Геттер поля number
     *
     * @return значение поля number
     * */
    public Integer getNumber()
    {
        return number;
    }

    /**
     * Сеттер поля number
     *
     * @param number - значение, которое необходимо присвоить
     *                 полю number
     * */
    public void setNumber(Integer number)
    {
        this.number = number;
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
       return "DONE_TASK " + number;
    }
}

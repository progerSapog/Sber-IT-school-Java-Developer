package homework.sber_it_school.events;

/**
 * Класс событие(event) - WRITE_MESSAGE.
 *
 * @see homework.sber_it_school.events.Event
 * */
public class WriteMessage implements Event
{
    public WriteMessage()
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
        return "WRITE_MESSAGE";
    }
}

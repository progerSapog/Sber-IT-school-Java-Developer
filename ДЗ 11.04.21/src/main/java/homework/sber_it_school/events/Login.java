package homework.sber_it_school.events;

/**
 * Класс событие(event) - LOGIN.
 *
 * @see homework.sber_it_school.events.Event
 * */
public class Login implements Event
{
    public Login()
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
        return "LOGIN";
    }
}

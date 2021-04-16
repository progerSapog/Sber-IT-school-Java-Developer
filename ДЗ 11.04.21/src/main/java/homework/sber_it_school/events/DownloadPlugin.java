package homework.sber_it_school.events;

/**
 * Класс событие(event) - DOWNLOAD_PLUGIN.
 *
 * @see homework.sber_it_school.events.Event
 * */
public class DownloadPlugin implements Event
{
    public DownloadPlugin()
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
        return "DOWNLOAD_PLUGIN";
    }
}

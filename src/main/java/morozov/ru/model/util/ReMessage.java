package morozov.ru.model.util;

/**
 * Некоторые сообщения, которые будут отправляться\приниматься контроллерами.
 * Для автоматической сериализации в\из json
 * @param <T>
 */
public interface ReMessage<T> {

    T getData();
    void setData(T message);
}

package morozov.ru.model.util;

public class ReMessageString implements ReMessage<String> {

    private String data;

    public ReMessageString() {
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public void setData(String message) {
        this.data = message;
    }
}

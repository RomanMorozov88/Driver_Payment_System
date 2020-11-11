package morozov.ru.model.util;

public class ReMessageDouble implements ReMessage<Double> {

    private Double data;

    public ReMessageDouble() {
    }

    @Override
    public Double getData() {
        return this.data;
    }

    @Override
    public void setData(Double message) {
        this.data = message;
    }
}

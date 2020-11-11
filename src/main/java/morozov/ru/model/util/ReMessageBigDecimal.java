package morozov.ru.model.util;

import java.math.BigDecimal;

public class ReMessageBigDecimal implements ReMessage<BigDecimal> {

    private BigDecimal data;

    public ReMessageBigDecimal() {
    }

    @Override
    public BigDecimal getData() {
        return this.data;
    }

    @Override
    public void setData(BigDecimal message) {
        this.data = message;
    }
}

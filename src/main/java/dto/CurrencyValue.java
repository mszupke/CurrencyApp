package dto;

import java.math.BigDecimal;
import java.util.Date;

public class CurrencyValue {

    private String baseCurrencyCode;
    private String currencyCode;
    private Date date;
    private BigDecimal value;

    public String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public void setBaseCurrencyCode(String baseCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CurrencyValue{" +
                "baseCurrencyCode='" + baseCurrencyCode + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", date=" + date +
                ", value=" + value +
                '}';
    }
}

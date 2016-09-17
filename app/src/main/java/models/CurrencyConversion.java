package models;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class CurrencyConversion {

    private String base;
    private String date;
    private Rates rates;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The base
     */
    public String getBase() {
        return base;
    }

    /**
     *
     * @param base
     * The base
     */
    public void setBase(String base) {
        this.base = base;
    }

    public CurrencyConversion withBase(String base) {
        this.base = base;
        return this;
    }

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    public CurrencyConversion withDate(String date) {
        this.date = date;
        return this;
    }

    /**
     *
     * @return
     * The rates
     */
    public Rates getRates() {
        return rates;
    }

    /**
     *
     * @param rates
     * The rates
     */
    public void setRates(Rates rates) {
        this.rates = rates;
    }

    public CurrencyConversion withRates(Rates rates) {
        this.rates = rates;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public CurrencyConversion withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
package net.harmelink.powerio.model2;

public class Consumption implements P1Model {
    private Double value;

    private Unit unit;

    public Double getValue() {
        return value;
    }

    public void setValue(final Double value) {
        this.value = value;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(final Unit unit) {
        this.unit = unit;
    }
}

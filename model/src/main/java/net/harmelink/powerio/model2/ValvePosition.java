package net.harmelink.powerio.model2;

public enum ValvePosition implements P1Model {
    ON(0),
    OFF(1),
    RELEASED(3);

    private Integer value;

    private ValvePosition(final Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}

package net.harmelink.powerio.model2;

public enum SwitchPosition implements P1Model {
    IN(0),
    OUT(1),
    ENABLED(3);

    private Integer value;

    private SwitchPosition(final Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}

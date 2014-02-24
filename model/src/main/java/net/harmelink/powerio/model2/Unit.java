package net.harmelink.powerio.model2;

public enum Unit implements P1Model {
    KWH("kWh"),
    KW("kW"),
    A("A"),
    M3("m3");

    private final String value;

    private Unit(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

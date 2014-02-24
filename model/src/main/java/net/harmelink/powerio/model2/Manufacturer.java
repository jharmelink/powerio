package net.harmelink.powerio.model2;

public enum Manufacturer implements P1Model {
    KMP("Kamstrup"),
    IS("IskraEmeco"),
    XE("Xemex"),
    NONE("Unknown");

    private final String name;

    private Manufacturer(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

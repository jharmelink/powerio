package net.harmelink.powerio;

public enum Supplier {
    KMP("Kamstrup"),
    ISk("IskraEmeco"),
    XMX("Xemex");

    private final String name;

    private Supplier(final String name) {
        this.name = name;
    }
}

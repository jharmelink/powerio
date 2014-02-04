package net.harmelink.powerio.mapper;

import net.harmelink.powerio.model.Supplier;

public class SupplierMapper {
    public static Supplier map(final String firstLine) {
        if (firstLine.contains(" KA")) {
            return new Supplier()
                    .withId(Supplier.Id.KA)
                    .withName(Supplier.Name.KAMSTRUP);
        }

        return new Supplier()
                .withId(Supplier.Id.UN)
                .withName(Supplier.Name.UNKNOWN);
    }
}

package net.harmelink.powerio.mapper;

public class ManufacturerMapper implements Mapper<String> {
    @Override
    public String map(final String supplierCode) {
        switch (supplierCode) {
            case "KA":
                return "Kamstrup";
            case "IS":
                return "IskraEmeco";
            case "XE":
                return "Xemex";
            default:
                return "Unknown";
        }
    }
}

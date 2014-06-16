package net.harmelink.powerio.mapper.p1;

import net.harmelink.powerio.mapper.RegexMapper;

public class ManufacturerMapper extends RegexMapper<String> {

    @Override
    protected String mapToObject(final String supplierCode) {
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

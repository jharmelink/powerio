package net.harmelink.powerio.mapper.p1;

import net.harmelink.powerio.mapper.AbstractMapper;
import net.harmelink.powerio.mapper.Mapper;

public class ManufacturerMapper extends AbstractMapper implements Mapper<String> {
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

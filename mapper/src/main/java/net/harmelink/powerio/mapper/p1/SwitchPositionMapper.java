package net.harmelink.powerio.mapper.p1;

import net.harmelink.powerio.mapper.AbstractMapper;
import net.harmelink.powerio.mapper.Mapper;

public class SwitchPositionMapper extends AbstractMapper implements Mapper<String> {
    @Override
    public String map(final String stringValue) {
        final int value = Integer.valueOf(stringValue);

        switch (value) {
            case 0:
                return "IN";
            case 1:
                return "OUT";
            case 2:
                return "ENABLED";
            default:
                return "UNKNOWN";
        }
    }
}

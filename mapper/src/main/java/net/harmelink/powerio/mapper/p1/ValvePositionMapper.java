package net.harmelink.powerio.mapper.p1;

import net.harmelink.powerio.mapper.AbstractMapper;
import net.harmelink.powerio.mapper.Mapper;

public class ValvePositionMapper extends AbstractMapper implements Mapper<String> {
    @Override
    public String map(final String valueString) {
        final int value = Integer.valueOf(valueString);

        switch (value) {
            case 0:
                return "ON";
            case 1:
                return "OFF";
            case 2:
                return "RELEASED";
            default:
                return "UNKNOWN";
        }
    }
}

package net.harmelink.powerio.mapper;

public class SwitchPositionMapper implements Mapper<String> {
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

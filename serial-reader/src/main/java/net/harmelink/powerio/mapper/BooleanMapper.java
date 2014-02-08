package net.harmelink.powerio.mapper;

public class BooleanMapper implements Mapper<Boolean> {
    public Boolean map(final String line) {
        final String value = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
        final int intValue = Integer.valueOf(value);

        return Boolean.valueOf(1 == intValue);
    }
}

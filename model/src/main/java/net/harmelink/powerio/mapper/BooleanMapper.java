package net.harmelink.powerio.mapper;

public class BooleanMapper implements Mapper<Boolean> {
    public Boolean map(final String line) {
        final String value = line.substring(line.indexOf('(', line.indexOf(')')));

        return Boolean.valueOf("1".equals(value));
    }
}

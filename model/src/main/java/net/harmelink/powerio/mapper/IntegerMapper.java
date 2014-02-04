package net.harmelink.powerio.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegerMapper implements Mapper<Integer> {
    private static final Logger LOG = LoggerFactory.getLogger(IntegerMapper.class);

    public Integer map(final String line) {
        final String value = line.substring(line.indexOf('(', line.indexOf(')')));

        try {
            return Integer.valueOf(value);
        } catch (final NumberFormatException e) {
            LOG.warn("Unable to map integer: {}", line);
        }

        return null;
    }
}

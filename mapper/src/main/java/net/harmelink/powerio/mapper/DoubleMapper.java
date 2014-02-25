package net.harmelink.powerio.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoubleMapper extends AbstractMapper implements Mapper<Double> {
    private static final Logger LOG = LoggerFactory.getLogger(DoubleMapper.class);

    @Override
    public Double map(final String doubleValue) {
        try {
            return new Double(doubleValue);
        } catch (final NumberFormatException e) {
            LOG.warn("Unable to create double from value: \"{}\".", doubleValue);
            return null;
        }
    }
}

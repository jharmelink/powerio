package net.harmelink.powerio.mapper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegerMapper implements Mapper<Integer> {
    private static final Logger LOG = LoggerFactory.getLogger(IntegerMapper.class);

    public Integer map(final String integerString) {
        if (!StringUtils.isEmpty(integerString)) {
            try {
                return Integer.valueOf(integerString);
            } catch (final NumberFormatException e) {
                LOG.warn("Unable to map integer: {}", integerString);
            }
        }

        return null;
    }
}

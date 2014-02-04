package net.harmelink.powerio.mapper;

import net.harmelink.powerio.model.Consumption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumptionMapper implements Mapper<Consumption> {
    private static final Logger LOG = LoggerFactory.getLogger(ConsumptionMapper.class);

    public Consumption map(final String line) {
        if (line.matches("^[0-9]-[0-9]:[0-9]\\.[0-9]\\.[0-9]\\([0-9]+\\.[0-9]+\\*.+\\)$")) {
            final String[] data = line.substring(line.indexOf('(', line.indexOf(')'))).split("\\*");

            return new Consumption()
                    .withValue(new Double(data[0]))
                    .withUnit(Consumption.Unit.fromValue(data[1]));
        }

        LOG.warn("Unable to map consumption: {}", line);
        return null;
    }
}

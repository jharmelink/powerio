package net.harmelink.powerio.mapper.p1;

import net.harmelink.powerio.mapper.RegexMapper;
import net.harmelink.powerio.model.Telegram.ValvePosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValvePositionMapper extends RegexMapper<ValvePosition> {

    private static final Logger LOG = LoggerFactory.getLogger(ValvePositionMapper.class);

    @Override
    public ValvePosition mapToObject(final String stringValue) {
        switch (stringValue) {
            case "0":
                return ValvePosition.ON;
            case "1":
                return ValvePosition.OFF;
            case "2":
                return ValvePosition.RELEASED;
            default:
                LOG.warn("Unable to map '{}' to ValvePosition", stringValue);
                return null;
        }
    }
}

package net.harmelink.powerio.mapper.p1;

import net.harmelink.powerio.mapper.RegexMapper;
import net.harmelink.powerio.model.Telegram.SwitchPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwitchPositionMapper extends RegexMapper<SwitchPosition> {

    private static final Logger LOG = LoggerFactory.getLogger(SwitchPositionMapper.class);

    @Override
    public SwitchPosition mapToObject(final String stringValue) {
        switch (stringValue) {
            case "0":
                return SwitchPosition.IN;
            case "1":
                return SwitchPosition.OUT;
            case "2":
                return SwitchPosition.ENABLED;
            default:
                LOG.warn("Unable to map '{}' to SwitchPosition", stringValue);
                return null;
        }
    }
}

package net.harmelink.powerio.mapper;

import net.harmelink.powerio.model.BusDevice;

import java.util.Arrays;
import java.util.List;

public class BusDeviceMapper extends ListRegexMapper<BusDevice> {

    public BusDeviceMapper() {
        super(BusDevice.class, "1", "2", "3", "4");
    }

    protected List<RegexMapping> getRegexMappings(final String regexPart) {
        return Arrays.asList(
                new RegexMapping("0-" + regexPart + ":24\\.1\\.0\\((.+?)\\)", "deviceType", IntegerMapper.class),
                new RegexMapping("\\(0-" + regexPart + ":24\\.2\\.1\\)(.+\\n.+)\\n", "totalConsumed",
                        ConsumptionMapper.class),
                new RegexMapping("0-" + regexPart + ":24\\.3\\.0\\((.+?)\\)", "measureDate", DateTimeMapper.class),
                new RegexMapping("0-" + regexPart + ":24\\.4\\.0\\((.+?)\\)", "valvePosition", ValvePositionMapper.class),
                new RegexMapping("0-" + regexPart + ":96\\.1\\.0\\((.+?)\\)", "equipmentIdentifier", HexMapper.class));
    }
}

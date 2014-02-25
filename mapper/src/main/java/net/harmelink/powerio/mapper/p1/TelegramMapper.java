package net.harmelink.powerio.mapper.p1;

import net.harmelink.powerio.mapper.*;
import net.harmelink.powerio.model.Telegram;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;

public class TelegramMapper extends SimpleRegexMapper<Telegram> {
    public TelegramMapper() {
        super(Telegram.class);
    }

    @Override
    public char getStartChar() {
        return '/';
    }

    @Override
    public char getEndChar() {
        return '!';
    }

    @Override
    protected void preMap(final Telegram telegram) {
        telegram.setTimestamp(new DateTime().toString());
    }

    protected List<RegexMapping> getRegexMappings() {
        return Arrays.asList(
                new RegexMapping("^(.+?)\\s", "manufacturer", ManufacturerMapper.class),
                new RegexMapping("^.*\\s(.+?)\\n", "serialNumber", StringMapper.class),
                new RegexMapping("0-0:96\\.1\\.1\\((.+?)\\)", "equipmentIdentifier", HexMapper.class),
                new RegexMapping("1-0:1\\.8\\.1\\((.+?)\\)", "totalPowerConsumedPeak", ConsumptionMapper.class),
                new RegexMapping("1-0:1\\.8\\.2\\((.+?)\\)", "totalPowerConsumedOffPeak", ConsumptionMapper.class),
                new RegexMapping("1-0:2\\.8\\.1\\((.+?)\\)", "totalPowerDeliveredPeak", ConsumptionMapper.class),
                new RegexMapping("1-0:2\\.8\\.2\\((.+?)\\)", "totalPowerDeliveredOffPeak", ConsumptionMapper.class),
                new RegexMapping("0-0:96\\.14\\.0\\((.+?)\\)", "powerTariffIndicator", StringMapper.class),
                new RegexMapping("1-0:1\\.7\\.0\\((.+?)\\)", "actualPowerConsumption", ConsumptionMapper.class),
                new RegexMapping("1-0:2\\.7\\.0\\((.+?)\\)", "actualPowerDelivery", ConsumptionMapper.class),
                new RegexMapping("0-0:17\\.0\\.0\\((.+?)\\)", "actualPowerThreshold", ConsumptionMapper.class),
                new RegexMapping("0-0:96\\.3\\.10\\((.+?)\\)", "powerSwitchPosition", SwitchPositionMapper.class),
                new RegexMapping("0-0:96\\.13\\.1\\((.+?)\\)", "textMessageCodes", StringMapper.class),
                new RegexMapping("0-0:96\\.13\\.0\\((.+?)\\)", "textMessage", StringMapper.class),
                new RegexMapping("^.*\\n\\n((.|\\n)+)$", "busDevices", BusDeviceMapper.class));
    }
}

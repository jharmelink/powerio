package net.harmelink.powerio.mapper.p1;

import com.google.inject.Inject;
import net.harmelink.powerio.mapper.HexMapper;
import net.harmelink.powerio.mapper.Mapper;
import net.harmelink.powerio.mapper.StringMapper;
import net.harmelink.powerio.model.Telegram;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P1Mapper implements Mapper {

    @Inject
    private ManufacturerMapper manufacturerMapper;

    @Inject
    private ConsumptionMapper consumptionMapper;

    @Inject
    private HexMapper hexMapper;

    @Inject
    private StringMapper stringMapper;

    @Inject
    private SwitchPositionMapper switchPositionMapper;

    @Inject
    private ValvePositionMapper valvePositionMapper;

    @Inject
    private DateTimeMapper dateTimeMapper;

    // TODO: replace by config file
    private static final Map<Integer, String> DEVICE_TYPES = new HashMap<Integer, String>() {{
        put(1, "gas");
        put(2, "water");
    }};

    public List<Telegram> map(final String data) {
        final List<Telegram> telegrams = new ArrayList<>();
        telegrams.add(new Telegram()
                .withTimestamp(ZonedDateTime.now().toString())
                .withManufacturer(manufacturerMapper.map(data, "^.+\\s(.{2}?)"))
                .withSerialNumber(stringMapper.map(data, "^.+\\s(.+?)\\n"))
                .withEquipmentIdentifier(hexMapper.map(data, "0-0:96\\.1\\.1\\((.+?)\\)"))
                .withTotalConsumedPeak(consumptionMapper.map(data, "1-0:1\\.8\\.1\\((.+?)\\)"))
                .withTotalConsumedOffPeak(consumptionMapper.map(data, "1-0:1\\.8\\.2\\((.+?)\\)"))
                .withTotalDeliveredPeak(consumptionMapper.map(data, "1-0:2\\.8\\.1\\((.+?)\\)"))
                .withTotalDeliveredOffPeak(consumptionMapper.map(data, "1-0:2\\.8\\.2\\((.+?)\\)"))
                .withTariffIndicator(stringMapper.map(data, "0-0:96\\.14\\.0\\((.+?)\\)"))
                .withActualConsumption(consumptionMapper.map(data, "1-0:1\\.7\\.0\\((.+?)\\)"))
                .withActualDelivery(consumptionMapper.map(data, "1-0:2\\.7\\.0\\((.+?)\\)"))
                .withActualThreshold(consumptionMapper.map(data, "0-0:17\\.0\\.0\\((.+?)\\)"))
                .withSwitchPosition(switchPositionMapper.map(data, "0-0:96\\.3\\.10\\((.+?)\\)"))
                .withTextMessageCodes(stringMapper.map(data, "0-0:96\\.13\\.1\\((.*?)\\)"))
                .withTextMessage(stringMapper.map(data, "0-0:96\\.13\\.0\\((.*?)\\)")));

        for (int i = 0; i < 4; i++) {
            if (data.contains("0-" + i + ":24")) {
                telegrams.add(new Telegram()
                        .withTimestamp(dateTimeMapper.map(data, "0-" + i + ":24\\.3\\.0\\((.+?)\\)"))
                        .withDeviceType(Telegram.DeviceType.fromValue(DEVICE_TYPES.get(i)))
                        .withTotalConsumedPeak(consumptionMapper.map(data, "\\(0-" + i + ":24\\.2\\.1\\)(.+\\n.+)\\n"))
                        .withValvePosition(valvePositionMapper.map(data, "0-" + i + ":24\\.4\\.0\\((.+?)\\)"))
                        .withEquipmentIdentifier(hexMapper.map(data, "0-" + i + ":96\\.1\\.0\\((.+?)\\)")));
            }
        }

        return telegrams;
    }
}

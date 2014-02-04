package net.harmelink.powerio.mapper;

import net.harmelink.powerio.model.Telegram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TelegramMapper {
    private static final Logger LOG = LoggerFactory.getLogger(TelegramMapper.class);

    private static final Map<String, MapperUnit> mappers = new HashMap<String, MapperUnit>() {{
        put("1-0:1.7.0", new MapperUnit("currentConsumption", ConsumptionMapper.class));
        put("1-0:2.7.0", new MapperUnit("currentDelivery", ConsumptionMapper.class));
        put("1-0:1.8.1", new MapperUnit("totalConsumptionOffPeak", ConsumptionMapper.class));
        put("1-0:1.8.2", new MapperUnit("totalConsumptionPeak", ConsumptionMapper.class));
        put("1-0:2.8.1", new MapperUnit("totalDeliveryOffPeak", ConsumptionMapper.class));
        put("1-0:2.8.2", new MapperUnit("totalDeliveryPeak", ConsumptionMapper.class));
        put("0-0:96.14.0", new MapperUnit("currentRate", IntegerMapper.class));
        put("0-0:96.3.10", new MapperUnit("currentSwitchPosition", BooleanMapper.class));
        put("0-0:96.13.0", new MapperUnit("message", StringMapper.class));
    }};

    public static Telegram map(final BufferedReader reader) throws IOException {
        LOG.debug("Mapping telegram...");
        final String firstLine = reader.readLine();
        final Telegram telegram = new Telegram()
                .withSupplier(SupplierMapper.map(firstLine))
                .withSerial(firstLine.substring(firstLine.indexOf(' ')))
                .withTimestamp(String.valueOf(System.currentTimeMillis()));

        while (reader.ready()) {
            final String line = reader.readLine();
            final String prefix = line.substring(0, line.charAt('('));
            final MapperUnit mapperUnit = mappers.get(prefix);

            try {
                Telegram.class.getField(mapperUnit.getField()).set(telegram, mapperUnit.getMapperClass().newInstance().map(line));
            } catch (IllegalAccessException | NoSuchFieldException | InstantiationException e) {
                LOG.warn("Unable to map {} with {}: {}", mapperUnit.getField(), mapperUnit.getMapperClass(), line);
            }
        }

        return telegram;
    }
}

package net.harmelink.powerio.mapper;

import net.harmelink.powerio.model.Telegram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TelegramMapper {
    private static final Logger LOG = LoggerFactory.getLogger(TelegramMapper.class);

    private static final String MAPPER_PACKAGE = "net.harmelink.powerio.mapper.";

    private static final String MAPPER_POSTFIX = "Mapper";

    private static final Map<String, String> mappers = new HashMap<String, String>() {{
        put("1-0:1.7.0", "currentConsumption");
        put("1-0:1.8.1", "totalConsumptionOffPeak");
        put("1-0:1.8.2", "totalConsumptionPeak");
        put("1-0:2.7.0", "currentDelivery");
        put("1-0:2.8.1", "totalDeliveryOffPeak");
        put("1-0:2.8.2", "totalDeliveryPeak");
        put("0-0:17.0.0", "maximumPower");
        put("0-1:24.1.0", "numberOfDevicesOnBus");
        put("0-1:24.4.0", "gasSwitchPosition");
        put("0-1:96.1.0", "gasMeterId");
        put("0-0:96.1.1", "powerMeterId");
        put("0-0:96.3.10", "powerSwitchPosition");
        put("0-0:96.13.0", "messageText");
        put("0-0:96.13.1", "messageCode");
        put("0-0:96.14.0", "offPeak");
    }};

    public static Telegram map(final BufferedReader reader) throws IOException {
        LOG.debug("Mapping telegram...");
        String line = reader.readLine();
        final Telegram telegram = new Telegram()
                .withSupplier(SupplierMapper.map(line))
                .withSerial(line.substring(line.indexOf(' ') + 1))
                .withTimestamp(String.valueOf(System.currentTimeMillis()));

        while ((line = reader.readLine()) != null) {
            if (line.matches("^[0-9]-[0-9]:[0-9]+\\.[0-9]+\\.[0-9]+\\(.*")) {
                final String prefix = line.substring(0, line.indexOf('('));
                final String fieldName = mappers.get(prefix);

                if (fieldName != null) {
                    try {
                        final Field field = Telegram.class.getDeclaredField(fieldName);
                        final Method method = getSetterForField(field);
                        method.invoke(telegram, getMapperForType(field.getType()).map(line));
                    } catch (final IllegalAccessException | NoSuchFieldException | InvocationTargetException e) {
                        LOG.warn("Unable to map '{}' to {}: {}", line, fieldName, e.getClass());
                    }
                }
            }
        }

        return telegram;
    }

    private static Mapper getMapperForType(final Class type) {
        try {
            return (Mapper) Class.forName(MAPPER_PACKAGE + type.getSimpleName() + MAPPER_POSTFIX).newInstance();
        } catch (final InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            LOG.error("Unable to find mapper for {}: {}", type.getClass(), e.getMessage());
        }

        return null;
    }

    private static Method getSetterForField(final Field field) {
        final String methodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

        try {
            return Telegram.class.getMethod(methodName, field.getType());
        } catch (final NoSuchMethodException e) {
            LOG.warn("No such method: {}", methodName);
        }

        return null;
    }
}

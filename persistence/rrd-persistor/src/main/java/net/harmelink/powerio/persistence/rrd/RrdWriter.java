package net.harmelink.powerio.persistence.rrd;

import net.harmelink.powerio.mapper.Mapper;
import net.harmelink.powerio.mapper.p1.TelegramMapper;
import net.harmelink.powerio.model.Telegram;
import net.harmelink.powerio.writer.AbstractWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RrdWriter extends AbstractWriter {

    private static final Logger LOG = LoggerFactory.getLogger(RrdWriter.class);

    private MongoWriter mongoWriter;

    public RrdWriter(final Mapper mapper) {
        super(mapper);
        mongoWriter = new MongoWriter("p1", 5000);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeMessage(final String message) {
        System.out.println("Message:\n" + message);
        final Telegram telegram;

        try {
            telegram = new TelegramMapper().map(message);
        } catch (final InstantiationException | IllegalAccessException e) {
            LOG.error(e.getMessage());
            return;
        }

        mongoWriter.write(System.currentTimeMillis(), telegram.getActualPowerConsumption().getValue());
    }
}


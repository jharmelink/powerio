package net.harmelink.powerio.writer.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.harmelink.powerio.mapper.Mapper;
import net.harmelink.powerio.mapper.p1.TelegramMapper;
import net.harmelink.powerio.model.Telegram;
import net.harmelink.powerio.writer.AbstractWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogWriter extends AbstractWriter {
    private static final Logger LOG = LoggerFactory.getLogger(LogWriter.class);

    public LogWriter(final Mapper mapper) {
        super(mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeMessage(final String message) {
        LOG.debug("Message:\n{}", message);
        final ObjectMapper mapper = new ObjectMapper();
        final Telegram telegram;

        try {
            telegram = new TelegramMapper().map(message);
        } catch (final InstantiationException | IllegalAccessException e) {
            LOG.error(e.getMessage());
            return;
        }

        try {
            LOG.debug(mapper.writeValueAsString(telegram));
        } catch (final JsonProcessingException e) {
            LOG.error("Unable to convert telegram to JSON string.");
        }
    }
}

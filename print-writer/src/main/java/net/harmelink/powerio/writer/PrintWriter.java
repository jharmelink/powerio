package net.harmelink.powerio.writer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.harmelink.powerio.mapper.Mapper;
import net.harmelink.powerio.mapper.p1.TelegramMapper;
import net.harmelink.powerio.model.Telegram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintWriter extends AbstractWriter {

    private static final Logger LOG = LoggerFactory.getLogger(PrintWriter.class);

    public PrintWriter(final Mapper mapper) {
        super(mapper);
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

        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            System.out.println("Output:\n" + mapper.writeValueAsString(telegram));
        } catch (final JsonProcessingException e) {
            LOG.error("Unable to convert telegram to JSON string.");
        }
    }
}

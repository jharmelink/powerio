package net.harmelink.powerio.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Inject;
import net.harmelink.powerio.mapper.Mapper;
import net.harmelink.powerio.model.Telegram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PrintWriter extends AbstractWriter {

    private static final Logger LOG = LoggerFactory.getLogger(PrintWriter.class);

    @Inject
    public PrintWriter(final Mapper mapper) {
        super(mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeMessage(final List<Telegram> telegrams) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            for (final Telegram telegram : telegrams) {
                System.out.println("Output:\n" + mapper.writeValueAsString(telegram));
            }
        } catch (final JsonProcessingException e) {
            LOG.error("Unable to convert telegram to JSON string.");
        }
    }
}

package net.harmelink.powerio.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogWriter extends AbstractWriter {
    private static final Logger LOG = LoggerFactory.getLogger(ModelWriter.class);

    public LogWriter(final char startChar, final char endChar) {
        super(startChar, endChar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeMessage(final String message) {
        LOG.debug("Message:\n{}", message);
    }
}

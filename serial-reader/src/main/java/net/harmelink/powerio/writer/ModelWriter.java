package net.harmelink.powerio.writer;

import net.harmelink.powerio.mapper.TelegramMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class ModelWriter extends AbstractWriter {

    public ModelWriter(final char startChar, final char endChar) {
        super(startChar, endChar);
    }

    /**
     * Maps a message {@link java.lang.String} to a {@link net.harmelink.powerio.model.Telegram}.
     *
     * @param message a message {@link String}
     * @throws IOException when mapping fails
     */
    @Override
    protected void writeMessage(final String message) throws IOException {
        TelegramMapper.map(new BufferedReader(new StringReader(message)));
    }
}

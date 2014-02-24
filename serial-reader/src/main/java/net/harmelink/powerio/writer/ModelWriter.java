package net.harmelink.powerio.writer;

import net.harmelink.powerio.mapper.TelegramMapper;
import net.harmelink.powerio.model.Telegram;

import java.io.IOException;

public class ModelWriter extends AbstractWriter {

    public ModelWriter(final char startChar, final char endChar) {
        super(startChar, endChar);
    }

    /**
     * Maps a message {@link java.lang.String} to a {@link net.harmelink.powerio.model2.Telegram}.
     *
     * @param message a message {@link String}
     * @throws IOException when mapping fails
     */
    @Override
    protected void writeMessage(final String message) throws IOException {
        try {
            final Telegram telegram = new TelegramMapper().map(message);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

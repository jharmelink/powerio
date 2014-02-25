package net.harmelink.powerio.writer.mongo;

import net.harmelink.powerio.mapper.Mapper;
import net.harmelink.powerio.mapper.p1.TelegramMapper;
import net.harmelink.powerio.model.Telegram;
import net.harmelink.powerio.writer.AbstractWriter;

import java.io.IOException;

public class MongoWriter extends AbstractWriter {

    public MongoWriter(final Mapper mapper) {
        super(mapper);
    }

    /**
     * Maps a message {@link java.lang.String} to a {@link Telegram}.
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

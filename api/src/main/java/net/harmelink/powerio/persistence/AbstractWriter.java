package net.harmelink.powerio.persistence;

import net.harmelink.powerio.mapper.Mapper;
import net.harmelink.powerio.model.Telegram;

import java.util.List;

public abstract class AbstractWriter implements Writer {

    private final Mapper mapper;

    protected AbstractWriter(final Mapper mapper) {
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final String input) {
        writeMessage(mapper.map(input));
    }

    protected abstract void writeMessage(final List<Telegram> telegrams);
}

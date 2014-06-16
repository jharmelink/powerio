package net.harmelink.powerio.persistence.rrd;

import com.google.inject.Inject;
import net.harmelink.powerio.mapper.Mapper;
import net.harmelink.powerio.model.Telegram;
import net.harmelink.powerio.persistence.AbstractWriter;

import java.util.List;

public class RrdWriter extends AbstractWriter {

    private final MongoWriter mongoWriter;

    @Inject
    public RrdWriter(final Mapper mapper) {
        super(mapper);
        mongoWriter = new MongoWriter("p1", 5000);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeMessage(final List<Telegram> telegrams) {
        mongoWriter.write(System.currentTimeMillis(), telegrams.get(0).getActualConsumption().getValue());
    }
}


package net.harmelink.powerio.mapper;

import net.harmelink.powerio.model.P1Model;

import java.util.List;

public abstract class SimpleRegexMapper<T extends P1Model> extends RegexMapper<T> implements Mapper<T> {
    public SimpleRegexMapper(final Class<T> modelClass) {
        super(modelClass);
    }

    protected abstract List<RegexMapping> getRegexMappings();

    public final T map(final String data) throws InstantiationException, IllegalAccessException {
        return super.map(data, getRegexMappings());
    }
}

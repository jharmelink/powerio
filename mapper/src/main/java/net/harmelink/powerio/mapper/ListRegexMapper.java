package net.harmelink.powerio.mapper;

import net.harmelink.powerio.model.P1Model;

import java.util.ArrayList;
import java.util.List;

public abstract class ListRegexMapper<T extends P1Model> extends RegexMapper<T> implements Mapper<List<T>> {
    private final String[] regexParts;

    public ListRegexMapper(final Class<T> modelClass, final String... regexParts) {
        super(modelClass);
        this.regexParts = regexParts;
    }

    protected abstract List<RegexMapping> getRegexMappings(final String regexPart);

    public final List<T> map(final String data) throws InstantiationException, IllegalAccessException {
        final List<T> ts = new ArrayList<>();

        for (final String regexPart : regexParts) {
            ts.add(super.map(data, getRegexMappings(regexPart)));
        }

        return ts;
    }
}

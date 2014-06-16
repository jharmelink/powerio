package net.harmelink.powerio.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RegexMapper<T> {

    private static final Logger LOG = LoggerFactory.getLogger(RegexMapper.class);

    public final T map(final String data, final String patternString) {
        final Pattern pattern = Pattern.compile(patternString);
        final Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            final String group = matcher.group(1);
            return group.isEmpty() ? null : mapToObject(group);
        } else {
            LOG.warn("No match found for pattern: {}", patternString);
        }

        return null;
    }

    protected abstract T mapToObject(final String data);
}

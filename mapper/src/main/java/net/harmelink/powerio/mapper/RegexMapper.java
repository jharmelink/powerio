package net.harmelink.powerio.mapper;

import net.harmelink.powerio.model.P1Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;

public abstract class RegexMapper<T extends P1Model> extends AbstractMapper {
    private static final Logger LOG = LoggerFactory.getLogger(RegexMapper.class);

    private final Class<T> modelClass;

    protected RegexMapper(final Class<T> modelClass) {
        this.modelClass = modelClass;
    }

    protected final T map(final String data, final List<RegexMapping> regexMappings)
            throws InstantiationException, IllegalAccessException {
        final T t = modelClass.newInstance();
        preMap(t);

        for (final RegexMapping regexMapping : regexMappings) {
            final Method method = regexMapping.getMethod(modelClass);
            final Matcher matcher = regexMapping.getPattern().matcher(data);

            if (matcher.find()) {
                final String matchedString = matcher.group(1);

                try {
                    method.invoke(t, regexMapping.getMapper().map(matchedString));
                } catch (final InvocationTargetException e) {
                    LOG.error("Unable to invoke {}.{}({})", modelClass.getName(), method.getName(), matchedString);
                }
                LOG.debug("{}: {}", method.getName(), matchedString);
            } else {
                LOG.error("No match found for pattern: {}", regexMapping.getPattern().toString());
            }
        }

        return t;
    }

    protected void preMap(final T t) {
        // Override this method
    }
}

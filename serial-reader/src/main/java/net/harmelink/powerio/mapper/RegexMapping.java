package net.harmelink.powerio.mapper;

import net.harmelink.powerio.model.P1Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class RegexMapping {
    private static final Logger LOG = LoggerFactory.getLogger(RegexMapping.class);

    private static final String SET = "set";

    private final Pattern pattern;

    private final String fieldName;

    private Mapper mapper;

    public RegexMapping(final String patternString, final String fieldName, final Class<? extends Mapper> mapperClass) {
        pattern = Pattern.compile(patternString);
        this.fieldName = fieldName;

        try {
            mapper = mapperClass.newInstance();
        } catch (final InstantiationException | IllegalAccessException e) {
            LOG.error("Unable to instantiate mapper for class: {}", mapperClass.getName());
            System.exit(1);
        }
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Method getMethod(final Class<? extends P1Model> modelClass) {
        final String methodName = SET + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

        try {
            final Class fieldClass = modelClass.getDeclaredField(fieldName).getType();

            try {
                return modelClass.getMethod(methodName, fieldClass);
            } catch (final NoSuchMethodException e) {
                LOG.error("No such method {}#{}({})", modelClass.getName(), methodName, fieldClass);
            }
        } catch (final NoSuchFieldException e) {
            LOG.error("No such field {}.{}", modelClass.getName(), fieldName);
        }

        return null;
    }

    public Mapper getMapper() {
        return mapper;
    }
}
